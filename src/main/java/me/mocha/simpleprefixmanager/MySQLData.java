package me.mocha.simpleprefixmanager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class MySQLData implements PrefixStorage {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS player_prefixes ("
            + "uuid VARCHAR(36) PRIMARY KEY, enabled TINYINT NOT NULL, prefix TEXT NOT NULL)";
    private static final String SELECT_PREFIX_SQL = "SELECT enabled, prefix FROM player_prefixes WHERE uuid = ?";
    private static final String UPSERT_PREFIX_SQL = "INSERT INTO player_prefixes (uuid, enabled, prefix) VALUES (?, ?, ?) "
            + "ON DUPLICATE KEY UPDATE enabled = VALUES(enabled), prefix = VALUES(prefix)";

    private final HikariDataSource dataSource;

    public MySQLData(SimplePrefixManager plugin) {
        String host = plugin.getConfig().getString("mysql.host", "localhost");
        int port = plugin.getConfig().getInt("mysql.port", 3306);
        String database = plugin.getConfig().getString("mysql.database", "minecraft");
        String user = plugin.getConfig().getString("mysql.user", "root");
        String password = plugin.getConfig().getString("mysql.password", "");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&serverTimezone=UTC");
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(5000);
        config.setIdleTimeout(300000);
        config.setMaxLifetime(600000);

        dataSource = new HikariDataSource(config);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            statement.execute();
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not initialize MySQL storage", exception);
        }
    }

    @Override
    public PrefixData load(Player player) throws IOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PREFIX_SQL)) {
            statement.setString(1, player.getUniqueId().toString());
            try (ResultSet results = statement.executeQuery()) {
                if (!results.next()) {
                    return new PrefixData(false, "");
                }
                return new PrefixData(results.getInt("enabled") == 1, results.getString("prefix"));
            }
        } catch (SQLException exception) {
            throw new IOException("Could not load prefix from MySQL", exception);
        }
    }

    @Override
    public void save(Player player, boolean enabled, String prefix) throws IOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPSERT_PREFIX_SQL)) {
            statement.setString(1, player.getUniqueId().toString());
            statement.setInt(2, enabled ? 1 : 0);
            statement.setString(3, prefix == null ? "" : prefix);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IOException("Could not save prefix to MySQL", exception);
        }
    }

    @Override
    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
