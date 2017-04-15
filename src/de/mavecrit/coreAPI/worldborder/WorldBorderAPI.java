package de.mavecrit.coreAPI.worldborder;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class WorldBorderAPI {
	
	public static void setBorder(World world, int size, Location center){
		 WorldBorder border = world.getWorldBorder();
         border.setSize(size);
         border.setCenter(center);
	}

}
