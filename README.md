# SimplePrefixManager

A lightweight, modern prefix manager for Bukkit, Spigot, and Paper servers.

SimplePrefixManager allows players to create their own chat prefixes with support for legacy color codes (`&`) and full RGB hex colors (`#RRGGBB`). Prefixes can be stored in either YAML or SQLite and are accessible through PlaceholderAPI.

## Features

- 🎨 Custom player prefixes
- 🌈 Legacy (&) and Hex RGB color support
- 💾 YAML or SQLite storage
- ⚡ Lightweight with minimal overhead
- 🔌 PlaceholderAPI integration
- 🧩 Built for Bukkit, Spigot, and Paper
- ✅ Compatible with Minecraft 1.20 - 1.26.2

## Requirements

- Java 8+
- Bukkit, Spigot, or Paper
- Minecraft 1.20 through 1.26.2

## Installation

1. Download the latest release.
2. Place `SimplePrefixManager.jar` into your server's `plugins` folder.
3. Start or restart your server.
4. Configure `config.yml` if desired.

## Configuration

```yaml
# Storage options
save-method: YAML

# Used when save-method is SQLITE
sqlite-database: prefixes.db

vault-support: false
```

### Storage Options

#### YAML

Stores prefixes inside:

```
plugins/SimplePrefixManager/prefix.yml
```

#### SQLite

Stores all prefix data inside a local SQLite database.

## Commands

| Command | Description |
|---------|-------------|
| `/prefix <text>` | Sets your custom prefix |
| `/prefix enable` | Enables your prefix |
| `/prefix disable` | Disables your prefix |

## Permissions

| Permission | Description |
|------------|-------------|
| `SPM.prefix` | Allows players to create, enable, and disable prefixes |
| `SPM.color` | Allows use of color codes and hex colors in chat |

## PlaceholderAPI

Placeholder:

```
%SPM_prefix%
```

Returns the player's currently enabled prefix.

## Color Support

Supports both:

```
&6Gold
&aGreen
```

and modern RGB colors:

```
#55FFFFHello
#FF5555Warning
```

## Storage Architecture

SimplePrefixManager uses an abstract storage system allowing administrators to choose between YAML or SQLite without changing player commands.

Current backends:

- YAML
- SQLite
- SQL

This architecture also makes future storage implementations easier to add.

## Contributing

Pull requests, issues, and feature suggestions are always welcome.

## License

MIT License (or whatever license you choose).
