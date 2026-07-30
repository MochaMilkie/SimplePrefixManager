package me.mocha.simpleprefixmanager;

import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class SQLData implements PrefixStorage {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS player_prefixes ("
            + "uuid TEXT PRIMARY KEY, enabled INTEGER NOT NULL, prefix TEXT NOT NULL)";
    private static final String SELECT_PREFIX_SQL = "SELECT enabled, prefix FROM player_prefixes WHERE uuid = ?";
    private static final String UPSERT_PREFIX_SQL = "INSERT INTO player_prefixes (uuid, enabled, prefix) VALUES (?, ?, ?) "
            + "ON CONFLICT(uuid) DO UPDATE SET enabled = excluded.enabled, prefix = excluded.prefix";

    private final Connection connection;

    public SQLData(SimplePrefixManager plugin) {
        String configuredPath = plugin.getConfig().getString("sqlite-database", "prefixes.db");
        File databaseFile = new File(configuredPath);
        if (!databaseFile.isAbsolute()) {
            databaseFile = new File(plugin.getDataFolder(), configuredPath);
        }

        File parentDirectory = databaseFile.getParentFile();
        if (parentDirectory != null && !parentDirectory.exists() && !parentDirectory.mkdirs()) {
            throw new IllegalStateException("Could not create SQLite database directory: " + parentDirectory);
        }

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getAbsolutePath());
            try (PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_SQL)) {
                statement.execute();
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not initialize SQLite storage", exception);
        }
    }

    @Override
    public synchronized PrefixData load(Player player) throws IOException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PREFIX_SQL)) {
            statement.setString(1, player.getUniqueId().toString());
            try (ResultSet results = statement.executeQuery()) {
                if (!results.next()) {
                    return new PrefixData(false, "");
                }
                return new PrefixData(results.getInt("enabled") == 1, results.getString("prefix"));
            }
        } catch (SQLException exception) {
            throw new IOException("Could not load prefix from SQLite", exception);
        }
    }

    @Override
    public synchronized void save(Player player, boolean enabled, String prefix) throws IOException {
        try (PreparedStatement statement = connection.prepareStatement(UPSERT_PREFIX_SQL)) {
            statement.setString(1, player.getUniqueId().toString());
            statement.setInt(2, enabled ? 1 : 0);
            statement.setString(3, prefix == null ? "" : prefix);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IOException("Could not save prefix to SQLite", exception);
        }
    }

    @Override
    public synchronized void close() {
        try {
            connection.close();
        } catch (SQLException exception) {
        }
    }
}
