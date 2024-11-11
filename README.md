# Overview
**EconomyBridge** is a simple, lightweight unified library plugin used to interact with multiple Bukkit economy/currency plugins with only a few lines of code.

## Features
- Easily **deposit**, **withdraw** and **check balance** of the currency.
- Easily **format** numeric values with the currency format.
- Handle currencies for **offline players** (if supported by the original plugin).
- Configurable currency's display name (if not provided by the original plugin).
- Configurable currency's format (if not provided by the original plugin).
- Configurable currency's icon (if not provided by the original plugin).
- **\[BETA\]** Create your own, custom ItemStack based currencies with full NBT support.

## Supported Plugins & Providers
- **Vault** - A Vault-compatible economy plugin is required (Vault is an API, not the economy itself).
- **XP Points** - Vanilla player's experience points.
- **XP Levels** - Vanilla player's experience levels.
- **BeastTokens**
- **CoinsEngine**
- **EliteMobs**
- **GemsEconomy**
- **PlayerPoints**
- **UltraEconomy**
- **VotingPlugin**

## Usage

### Currency ID
Every currency gets unique internal string ID that you use in other plugin's configurations, or in your code.

If you have doubts, in plugin startup logs you will see ID of each currency registered by the plugin.

| Provider | ID |
| -------- | -- |
| Vault | `vault` |
| XP Points | `xp_points` |
| XP Levels | `xp_levels` |
| BeastTokens | `beasttokens` |
| CoinsEngine | `coinsengine_` + `currency_ID`<br>`coinsengine_coins` |
| EliteMobs | `elitemobs` |
| GemsEconomy| `gemseconomy_` + `currency singular name` |
| PlayerPoints | `playerpoints` |
| UltraEconomy | `ultraeconomy_` + `currency name` |
| VotingPlugin | `votingplugin` |

### Regular Users
For regular users, there is **EconomyBridge** plugin folder with configuration files.

The most useful ones are **currencies.yml** and **items.yml** - this is where you can edit currency settings and create item-based currencies.

### Developers
For developers there is `EconomyBridge` class that provides all utility methods to work with currencies.

You can obtain currency IDs from the `CurrencyId` class.

```java
 // Check if currency exists.
EconomyBridge.hasCurrency(String id);

// Do something with a currency if it does exist.
EconomyBridge.handle(String id, Consumer<Currency> consumer);

 // Check if player's balance >= amount.
EconomyBridge.hasEnough(Player player, String id, double amount);
EconomyBridge.hasEnough(UUID playerId, String id, double amount);

 // Get player's balance.
EconomyBridge.getBalance(Player player, String id);
EconomyBridge.getBalance(UUID playerId, String id);

// Deposit to player's balance.
EconomyBridge.deposit(Player player, String id, double amount);
EconomyBridge.deposit(UUID playerId, String id, double amount);

// Withdraw from player's balance.
EconomyBridge.withdraw(Player player, String id, double amount);
EconomyBridge.withdraw(UUID playerId, String id, double amount);

// Check if there is any currency.
boolean hasCurrency = EconomyBridge.hasCurrency();

// Get currency manager.
CurrencyManager manager = EconomyBridge.getCurrencyManager();

// Get all currencies.
Set<Currency> currencies = EconomyBridge.getCurrencies();

// Get all currency IDs.
Set<String> ids = EconomyBridge.getCurrencyIds();

// Get currency by ID.
Currency = EconomyBridge.getCurrency(String internalId);

// Get currency by ID or Dummy currency instance.
Currency = EconomyBridge.getCurrencyOrDummy(String internalId);

// Get dummy currency.
DummyCurrency dummy = EconomyBridge.getDummyCurrency();
```
