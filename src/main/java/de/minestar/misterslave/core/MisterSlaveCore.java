/*
 * Copyright (C) 2012 MineStar.de 
 * 
 * This file is part of MisterSlave.
 * 
 * MisterSlave is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * MisterSlave is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MisterSlave.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.minestar.misterslave.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.minestar.minestarlibrary.AbstractCore;
import de.minestar.minestarlibrary.utils.ConsoleUtils;

public class MisterSlaveCore extends AbstractCore {

    public final static String NAME = "MisterSlave";

    private static Plugin contao;
    private static Plugin INSTANCE;

    private static Runnable disableThread;

    public MisterSlaveCore() {
        super(NAME);
        INSTANCE = this;
    }

    @Override
    protected boolean createThreads() {
        disableThread = new Runnable() {
            @Override
            public void run() {
                if (contao.isEnabled()) {

                    Bukkit.broadcastMessage(ChatColor.RED + "[SERVER] Das Plugin 'Contao' wird wegen einem Datenbankfehler neugestartet.");
                    Bukkit.broadcastMessage(ChatColor.RED + "[SERVER] Das Team arbeitet an einer feineren Lösung.");
                    ConsoleUtils.printInfo(NAME, "Contao is disabling!");
                    Bukkit.getPluginManager().disablePlugin(contao);
                    ConsoleUtils.printInfo(NAME, "Contao has disabled!");
                } else {
                    ConsoleUtils.printError(NAME, "Contao was already disabled!");
                    return;
                }
                if (!contao.isEnabled()) {
                    ConsoleUtils.printInfo(NAME, "Contao is enabling!");
                    Bukkit.getPluginManager().enablePlugin(contao);
                    ConsoleUtils.printInfo(NAME, "Contao has enabled!");
                } else {
                    ConsoleUtils.printError(NAME, "Contao was already enabled!");
                    return;
                }
            }
        };
        return true;
    }

    @Override
    protected boolean commonEnable() {
        contao = (JavaPlugin) Bukkit.getPluginManager().getPlugin("ContaoTwo");

        return true;
    }

    public static void restartContao() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(INSTANCE, disableThread, 1L);
    }

}
