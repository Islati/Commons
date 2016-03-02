package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.Position;
import com.caved_in.commons.yml.data.RealConfig;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RealConfigurationSerializeTest {
    private static RealConfig realConfig;
    private static File file;

    @BeforeClass
    public static void before() {
        realConfig = new RealConfig();

        file = new File("temp", "realConfig.yml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void test3changePosition() throws InvalidConfigurationException {
        realConfig.getSetup_lobbyPosition().setX(123);
        realConfig.getSetup_lobbyPosition().setY(135);
        realConfig.getSetup_lobbyPosition().setZ(456);
        realConfig.save();

        RealConfig config = new RealConfig();
        config.load(file);

        Assert.assertEquals(config.getSetup_lobbyPosition().getX(), 123);
        Assert.assertEquals(config.getSetup_lobbyPosition().getY(), 135);
        Assert.assertEquals(config.getSetup_lobbyPosition().getZ(), 456);
        Assert.assertTrue(config.getSetup_spawnPosition().get(0) instanceof Position);
    }

    @Test
    public void test2doubleCommentFix() throws IOException, InvalidConfigurationException {
        realConfig.save();

        Assert.assertEquals(Util.readFileSplit(file), new String[]{"# Database configuration.",
                "database:",
                "  # The database JDBC address. Should replace dbname with the database name.",
                "  address: jdbc:mysql://localhost/dbname",
                "  # The table to use within given database",
                "  table: RushPlugin",
                "  # Database username, if applicable. Leave empty if unneeded",
                "  username: ''",
                "  # Database password, if applicable. Leave empty if unneeded",
                "  password: ''",
                "# Gameplay settings",
                "gameplay:",
                "  # Delay in minutes during which players cannot break beds.",
                "  bedBreakDelay: 60",
                "  # Amount of players each team needs before starting the game.",
                "  playersPerTeam: 4",
                "  # Amount of teams in the game.",
                "  # Can be 2, 3, 4 or 6",
                "  teams: 2",
                "  # Delay in seconds before starting the game",
                "  gameStartDelay: 10",
                "  # Delay in seconds for forced respawn after death.",
                "  respawnDelay: 10",
                "# Score system settings",
                "scoring:",
                "  # Points earned when winning the game",
                "  win: 4",
                "  # Points lost when losing the game",
                "  lose: 2",
                "  # Points earned when killing an enemy",
                "  kill: 2",
                "  # Points lost when getting killed",
                "  death: 1",
                "# Economy settings",
                "economy:",
                "  # Money earned when winning the game",
                "  win: 4",
                "  # Money lost when losing the game",
                "  lose: 2",
                "  # Points earned when killing an enemy",
                "  kill: 2",
                "  # Points lost when getting killed",
                "  death: 1",
                "# Game Setup",
                "setup:",
                "  # Game world",
                "  world: world",
                "  # Server to move players to after game over",
                "  hubServer: hub",
                "  # Position of the lobby",
                "  lobbyPosition:",
                "    x: 0",
                "    y: 50",
                "    z: 0",
                "  # Position of each spawns",
                "  spawnPosition:",
                "  - x: 0",
                "    y: 50",
                "    z: 0",
                "  - x: 0",
                "    y: 50",
                "    z: 0",
                "  - x: 0",
                "    y: 50",
                "    z: 0",
                "  - x: 0",
                "    y: 50",
                "    z: 0",
                "  - x: 0",
                "    y: 50",
                "    z: 0",
                "  - x: 0",
                "    y: 50",
                "    z: 0",
                "  - x: 0",
                "    y: 50",
                "    z: 0",
                "  - x: 0",
                "    y: 50",
                "    z: 0"
        });

    }

    @Test
    public void initNull() throws InvalidConfigurationException, IOException {
        realConfig.init(file);

        Assert.assertEquals(Util.readFileSplit(file), new String[]{"# Database configuration.",
                "database:",
                "  # The database JDBC address. Should replace dbname with the database name.",
                "  address: jdbc:mysql://localhost/dbname",
                "  # The table to use within given database",
                "  table: RushPlugin",
                "  # Database username, if applicable. Leave empty if unneeded",
                "  username: ''",
                "  # Database password, if applicable. Leave empty if unneeded",
                "  password: ''",
                "# Gameplay settings",
                "gameplay:",
                "  # Delay in minutes during which players cannot break beds.",
                "  bedBreakDelay: 60",
                "  # Amount of players each team needs before starting the game.",
                "  playersPerTeam: 4",
                "  # Amount of teams in the game.",
                "  # Can be 2, 3, 4 or 6",
                "  teams: 2",
                "  # Delay in seconds before starting the game",
                "  gameStartDelay: 10",
                "  # Delay in seconds for forced respawn after death.",
                "  respawnDelay: 10",
                "# Score system settings",
                "scoring:",
                "  # Points earned when winning the game",
                "  win: 4",
                "  # Points lost when losing the game",
                "  lose: 2",
                "  # Points earned when killing an enemy",
                "  kill: 2",
                "  # Points lost when getting killed",
                "  death: 1",
                "# Economy settings",
                "economy:",
                "  # Money earned when winning the game",
                "  win: 4",
                "  # Money lost when losing the game",
                "  lose: 2",
                "  # Points earned when killing an enemy",
                "  kill: 2",
                "  # Points lost when getting killed",
                "  death: 1",
                "# Game Setup",
                "setup:",
                "  # Game world",
                "  world: world",
                "  # Server to move players to after game over",
                "  hubServer: hub",
                "  # Position of the lobby",
                "  lobbyPosition:",
                "    x: 0",
                "    y: 50",
                "    z: 0",
                "  # Position of each spawns",
                "  spawnPosition:",
                "  - x: 0",
                "    y: 50",
                "    z: 0",
                "  - x: 0",
                "    y: 50",
                "    z: 0",
                "  - x: 0",
                "    y: 50",
                "    z: 0",
                "  - x: 0",
                "    y: 50",
                "    z: 0",
                "  - x: 0",
                "    y: 50",
                "    z: 0",
                "  - x: 0",
                "    y: 50",
                "    z: 0",
                "  - x: 0",
                "    y: 50",
                "    z: 0",
                "  - x: 0",
                "    y: 50",
                "    z: 0"
        });
    }
}
