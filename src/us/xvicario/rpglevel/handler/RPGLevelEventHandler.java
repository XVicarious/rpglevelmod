package us.xvicario.rpglevel.handler;

import java.lang.reflect.Field;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import us.xvicario.rpglevel.RPGLevel;
import us.xvicario.rpglevel.util.PlayerInformation;

public class RPGLevelEventHandler {
	@ForgeSubscribe
	public void playerInteractEvent(PlayerInteractEvent event) {
		if (event.entityPlayer.getHeldItem() != null) {
			PlayerInformation pi = PlayerInformation
					.forPlayer(event.entityPlayer);
			if ((event.entityPlayer.getHeldItem().getItem() instanceof ItemPickaxe)) {
				float properEfficiency = ((ItemPickaxe) event.entityPlayer.getHeldItem().getItem()).efficiencyOnProperMaterial;
				float playerLevel = pi.getMiningLevel();
				((ItemPickaxe) event.entityPlayer.getHeldItem().getItem()).efficiencyOnProperMaterial += ((playerLevel * RPGLevel.levelMultiplier) + 1.0D);
				((ItemPickaxe) event.entityPlayer.getHeldItem().getItem()).efficiencyOnProperMaterial = properEfficiency;
			} else if ((event.entityPlayer.getHeldItem().getItem() instanceof ItemSpade)) {
				float properEfficiency = ((ItemSpade) event.entityPlayer.getHeldItem().getItem()).efficiencyOnProperMaterial;
				float playerLevel = pi.getMiningLevel();
				((ItemSpade) event.entityPlayer.getHeldItem().getItem()).efficiencyOnProperMaterial += ((playerLevel * RPGLevel.levelMultiplier) + 1.0D);
				((ItemSpade) event.entityPlayer.getHeldItem().getItem()).efficiencyOnProperMaterial = properEfficiency;
			} else if ((event.entityPlayer.getHeldItem().getItem() instanceof ItemAxe)) {
				float properEfficiency = ((ItemAxe) event.entityPlayer.getHeldItem().getItem()).efficiencyOnProperMaterial;
				float playerLevel = pi.getWoodcuttingLevel();
				((ItemAxe) event.entityPlayer.getHeldItem().getItem()).efficiencyOnProperMaterial += ((playerLevel * RPGLevel.levelMultiplier) + 1.0D);
				((ItemAxe) event.entityPlayer.getHeldItem().getItem()).efficiencyOnProperMaterial = properEfficiency;
			}
		}
	}

	@ForgeSubscribe
	public void attackEntityEvent(AttackEntityEvent event) {
		if (event.entityPlayer.getHeldItem() != null) {
			PlayerInformation pi = PlayerInformation
					.forPlayer(event.entityPlayer);
			if ((event.entityPlayer.getHeldItem() != null) && (event.entityPlayer.getHeldItem().getItem() instanceof ItemSword)) {
				//((ItemSword)event.entityPlayer.getHeldItem().getItem()).weaponDamage
				float weaponDamage = (Float) getField(((ItemSword)event.entityPlayer.getHeldItem().getItem()),"weaponDamage");
				setField(((ItemSword)event.entityPlayer.getHeldItem().getItem()), "weaponDamage", weaponDamage + (pi.getAttackLevel() * RPGLevel.levelMultiplier));
				setField(((ItemSword)event.entityPlayer.getHeldItem().getItem()), "weaponDamage", weaponDamage);
				//event.target.attackEntityFrom(DamageSource.generic, event.entityPlayer.getHeldItem().getItem().getDamageVsEntity(event.target, event.entityPlayer.getHeldItem())*(pi.getAttackLevel()));
			} else {
				;
			}
		}
	}
	
	@ForgeSubscribe
	public void playerHurtEvent(LivingHurtEvent event) {
		if (event.entity instanceof EntityPlayer) {
			PlayerInformation pi = PlayerInformation.forPlayer(event.entity);
			for (int i = 0; i < 4; i++) {
				if (((EntityPlayer)event.entity).inventory.armorItemInSlot(i) != null && event.source == DamageSource.generic) {
					((EntityPlayer)event.entity).heal((float) (((ItemArmor)((EntityPlayer)event.entity).inventory.armorItemInSlot(i).getItem()).damageReduceAmount * (pi.getDefenceLevel() * RPGLevel.levelMultiplier)));
				}
			}
			System.out.println(((EntityPlayer)event.entity).getHealth() + "");
		}
	}

	@ForgeSubscribe
	public void onEntityConstruct(EntityEvent.EntityConstructing event) {
		if ((event.entity instanceof EntityPlayer)) {
			event.entity.registerExtendedProperties("rpglevel_data",
					new PlayerInformation((EntityPlayer) event.entity));
		}
	}
	
	public void setField(Object theClass, String fieldName, Object value) {
		try {
			Field field = theClass.getClass().getDeclaredField(fieldName);
			try {
				field.set(fieldName, value);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Object getField(Object theClass, String fieldName) {
		Field field = null;
		Object fieldValue = null;
		try {
			field = theClass.getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fieldValue = field.get(field);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fieldValue;
	}
}