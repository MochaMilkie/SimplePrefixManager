## Overview
SimplePrefixManager allows players to assign custom prefixes, and adds hex color code support in chat.

## Features
- Manage player prefixes with simple commands
- Lightweight and efficient
- PlaceholderAPI support ("SPM_prefix")
- SQLite storage support
- Vault Support (coming soon)

## Installation
1. Download the latest release from the [releases page](https://github.com/MochaMilkie/SimplePrefixManager-1.21/releases).
2. Place the downloaded `.jar` file into the `plugins` directory of your Minecraft server.
3. Restart your server to load the plugin.

## Permissions
- SPM.prefix | Allows use of, disabling and enabling of a prefix.
- SPM.color | Allows use of hex color codes in prefix, and chat.

## Storage
Set `save-method` in `config.yml` to `YAML` (the default) or `SQLITE`. SQLite creates a local database in the plugin data folder; its filename is controlled by `sqlite-database` and defaults to `prefixes.db`.


## Contributing
1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add new feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Create a new Pull Request.

## Contact
For any issues or feature requests, please open an issue on this repository.
