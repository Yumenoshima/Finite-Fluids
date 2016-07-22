package com.mcfht.realisticfluids;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.mcfht.realisticfluids.fluids.BlockFiniteFluid;

/**
 * Contains some handy dandy thingies to make my life easier.
 * 
 * <p>
 * Also provides an interface which which to more easily access various methods
 * that are scattered around some of the horribly messy parts of my code.
 * 
 * @author FHT
 * 
 */
public class Util
{

	/** Array of relative x,y,z directions */
	public static final int[][]	directions	=
											{
											{0, 1},
											{1, 0},
											{0, -1},
											{-1, 0},
											{-1, 1},
											{1, 1},
											{1, -1},
											{-1, -1}};
	public static final Random	r			= new Random();
	/** used to retrieve and shuffle random directions */
	public static int			offset		= r.nextInt(8);

	/**
	 * Gets an 8-directional dx from an integer
	 * 
	 * @param dir
	 * @return
	 */
	public static int intDirX(final int dir)
	{
		return directions[dir & 0x7][0];
	}
	/**
	 * Gets an 8-directional dz from an integer
	 * 
	 * @param dir
	 * @return
	 */
	public static int intDirZ(final int dir)
	{
		return directions[dir & 0x7][1];
	}

	/**
	 * Gets a cardinal (N-S) dx from an integer
	 * 
	 * @param dir
	 * @return
	 */
	public static int cardinalX(final int dir)
	{
		return directions[dir & 0x3][0];
	}
	/**
	 * Gets an cardinal (E-W) dz from an integer
	 * 
	 * @param dir
	 * @return
	 */
	public static int cardinalZ(final int dir)
	{
		return directions[dir & 0x3][1];
	}

	/**
	 * Calculates a pseudo random X/Z vector, consecutive calls will cycleall 8
	 * directions.
	 * 
	 * @return
	 */
	public static int[] nextXZ()
	{
		offset = (++offset & 0x7);
		return new int[]
		{directions[offset][0], directions[offset][1]};
	}

	/**
	 * Compares fluids. Assumes that b0 <b>is</b> a fluid
	 * 
	 * @param b0
	 * @param b1
	 * @return
	 */
	public static boolean isSameFluid(final Block b0, final Block b1)
	{
		return (b1 instanceof BlockFiniteFluid && b0.getMaterial() == b1.getMaterial());
	}

	public static String intStr(final int... i)
	{
		String out = " [";
		for (final int val : i)
			out += val + ", ";
		out.substring(0, out.length() - 2);
		return out + "]";
	}

    public static int fluidTo8th(int fluid)
    {
        if (0 == fluid)
            return 0;   // Technically, this isn't needed :-).
/* This doesn't work!
 * Java actually does not round down uniformly.
 * -8 / (1<<8) gives -1 (Expected)
 * -8 / (1<<9) gives 0 (breaks this)
        int wholePart = (fluid-1) * 8 / RealisticFluids.MAX_FLUID; // -1 gets rounding correct;
        // consider fluid of exactly 1/8th.
        return 1+wholePart;
*/
        // Think of this: (Fluid-1) / Max_fluid, as a float from 0 to 1, scaled to 0-7.
        float top = (fluid-1) * 8;
        float div = top / RealisticFluids.MAX_FLUID;
        int result = (int) Math.floor(div);
        return result + 1;
    }

	public static int getMetaFromLevel(final int l)
	{
	//	return Math.max(0, 7 - (l / (RealisticFluids.MAX_FLUID >> 3)));
	    return 8-fluidTo8th(l);
	}

	public static int getRotationFromEntity(final World w, final int x, final int y, final int z, final EntityLivingBase placer)
	{
		if (MathHelper.abs((float) placer.posX - x) < 2.0F && MathHelper.abs((float) placer.posZ - z) < 2.0F)
		{
			final double d0 = placer.posY + 1.82D - placer.yOffset;

			if (d0 - y > 2.0D)
				return 1;

			if (y - d0 > 0.0D)
				return 0;
		}

		final int l = MathHelper.floor_double(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
	}

}
