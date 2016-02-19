/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Psi Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: http://psi.vazkii.us/license.php
 *
 * File Created @ [08/02/2016, 16:15:38 (GMT)]
 */
package vazkii.psi.common.entity;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntitySpellGrenade extends EntitySpellProjectile {

	boolean sound = false;

	public EntitySpellGrenade(World worldIn) {
		super(worldIn);
	}

	public EntitySpellGrenade(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);

		double speed = 0.65;
		motionX *= speed;
		motionY *= speed;
		motionZ *= speed;
	}

	@Override
	protected float getGravityVelocity() {
		return 0.05F;
	}

	@Override
	public void onUpdate() {
		int alive = dataWatcher.getWatchableObjectInt(23);
		super.onUpdate();

		if(alive > 60 && !isDead && explodes())
			doExplosion();
	}

	public void doExplosion() {
		cast();
		playSound("random.explode", 0.5F, 1F);
		double m = 0.1;
		double d3 = 0.0D;
		for(int j = 0; j < 40; j++) {
			double d0 = worldObj.rand.nextGaussian() * m;
			double d1 = worldObj.rand.nextGaussian() * m;
			double d2 = worldObj.rand.nextGaussian() * m;

			worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, posX + worldObj.rand.nextFloat() * width * 2.0F - width - d0 * d3, posY + worldObj.rand.nextFloat() * height - d1 * d3, posZ + worldObj.rand.nextFloat() * width * 2.0F - width - d2 * d3, d0, d1, d2);
		}
	}

	public boolean explodes() {
		return true;
	}

	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if(!worldObj.isRemote && !sound && explodes()) {
			playSound("creeper.primed", 2F, 1F);
			sound = true;
		}

		posX = pos.hitVec.xCoord;
		posY = pos.hitVec.yCoord;
		posZ = pos.hitVec.zCoord;
		motionX = 0;
		motionY = 0;
		motionZ = 0;
	}

}
