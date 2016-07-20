package com.mcfht.realisticfluids;

import cpw.mods.fml.common.ModMetadata;
public class FluidModInfo {

    public static final String MODID = "finitewater";
    public static final String VERSION = "A5.0-fastflow-Spammy-slowrain";
    public static final String AUTHOR = "FHT";
	
	public static void get(ModMetadata meta)
	{
		meta.modId = MODID;
		meta.name = MODID;
		meta.version = VERSION;
		meta.credits = AUTHOR + ", Keybounce";
		meta.authorList.add(AUTHOR);
		meta.description= "Finite, flowing water and lava, more realistic than forge fluids";
		meta.url = "";
		meta.updateUrl= "";
		meta.screenshots= new String[0];
		meta.logoFile = "";
	}
	
}
