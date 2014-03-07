package us.xvicario.rpglevel.block;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Random;

import us.xvicario.rpglevel.RPGLevel;
import us.xvicario.rpglevel.client.gui.LevelUpStationGUI;
import us.xvicario.rpglevel.tileentity.TileEntityLevelUpStation;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBook;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLevelUpStation extends BlockContainer {
	private Icon topIcon;
	private Icon bottomIcon;
	private Icon sideIcon;

public BlockLevelUpStation(int par1, Material par2Material, boolean active) {
		super(par1, par2Material);
		this.setHardness(10.1F);
		this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		this.bottomIcon = icon.registerIcon("rpglevel:levelup");
		this.topIcon = icon.registerIcon("rpglevel:levelup_top");
		this.sideIcon = icon.registerIcon("rpglevel:levelup_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		if (side == 0) {
			return this.bottomIcon;
		} else if (side == 1) {
			return this.topIcon;
		} else {
			return this.sideIcon;
		}
	}
	
	public int isDropped(int par1, Random rand, int par2) {
		return this.blockID;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote && isMultiBlockStructure(world,x+1,y,z+1)) {
			BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(x, z);
			doPacketForSkill(biome,player,Type.MOUNTAIN,Item.pickaxeIron);
			doPacketForSkill(biome,player,Type.FOREST,Item.axeIron);
			doPacketForSkill(biome,player,Type.FOREST,Item.bow);
			
		}
		return true;
	}
	
	private void doPacketForSkill(BiomeGenBase biome , EntityPlayer player, Type type, Item item) {
		if (BiomeDictionary.isBiomeOfType(biome, type)) {
			if (player.getHeldItem() != null) {
				if (player.getHeldItem().getItem() == item) {
					actionPerformed(1);
					player.addChatMessage("You had an " + item.getUnlocalizedName() +"!");
				}
			}
		}
	}
	
	private void actionPerformed(int skill) {
		boolean flag = false;
		if (flag == false) {
			ByteArrayOutputStream bos1 = new ByteArrayOutputStream(5);
			DataOutputStream outputStream = new DataOutputStream(bos1);
			try {
				outputStream.writeInt(skill);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "RPGLEVEL";
			packet.data = bos1.toByteArray();
			packet.length = bos1.size();
			PacketDispatcher.sendPacketToServer(packet);
		}
			flag = true;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityLevelUpStation();
	}
	
	public boolean isMultiBlockStructure(World world, int x, int y, int z) {
		if (checkNorth(world, x, y, z))/* North */
			return true;
		if (checkEast(world, x, y, z))/* East */
			return true;
		if (checkSouth(world, x, y, z))/* South */
			return true;
		if (checkWest(world, x, y, z))/* West */
			return true;
		return false;
	}
	
	private static boolean checkNorth(World world, int x, int y, int z) {
		if (world.getBlockId(x + 0, y + 0, z + -1) == 55) {
			if (world.getBlockId(x + 0, y + 0, z + -2) == RPGLevel.blockEEOID) {
				if (world.getBlockId(x + 0, y + 1, z + 0) == 55) {
					if (world.getBlockId(x + 0, y + 1, z + -1) == 0) {
						if (world.getBlockId(x + 0, y + 1, z + -2) == 55) {
							if (world.getBlockId(x + 0, y + 2, z + 0) == 0) {
								if (world.getBlockId(x + 0, y + 2, z + -1) == 0) {
									if (world.getBlockId(x + 0, y + 2, z + -2) == 0) {
										if (world.getBlockId(x + 1, y + 0,
												z + 0) == 55) {
											if (world.getBlockId(x + 1, y + 0,
													z + -1) == RPGLevel.blockLUSID) {
												if (world.getBlockId(x + 1,
														y + 0, z + -2) == 55) {
													if (world.getBlockId(x + 1,
															y + 1, z + 0) == 0) {
														if (world.getBlockId(
																x + 1, y + 1, z
																		+ -1) == 0) {
															if (world.getBlockId(
																			x + 1,
																			y + 1,
																			z
																					+ -2) == 0) {
																if (world.getBlockId(
																				x + 1,
																				y + 2,
																				z + 0) == 0) {
																	if (world.getBlockId(
																					x + 1,
																					y + 2,
																					z
																							+ -1) == 0) {
																		if (world.getBlockId(
																						x + 1,
																						y + 2,
																						z
																								+ -2) == 0) {
																			if (world.getBlockId(
																							x + 2,
																							y + 0,
																							z + 0) == RPGLevel.blockEEOID) {
																				if (world.getBlockId(
																								x + 2,
																								y + 0,
																								z
																										+ -1) == 55) {
																					if (world.getBlockId(
																									x + 2,
																									y + 0,
																									z
																											+ -2) == RPGLevel.blockEEOID) {
																						if (world.getBlockId(
																										x + 2,
																										y + 1,
																										z + 0) == 55) {
																							if (world.getBlockId(
																											x + 2,
																											y + 1,
																											z
																													+ -1) == 0) {
																								if (world.getBlockId(
																												x + 2,
																												y + 1,
																												z
																														+ -2) == 55) {
																									if (world.getBlockId(
																													x + 2,
																													y + 2,
																													z + 0) == 0) {
																										if (world.getBlockId(
																														x + 2,
																														y + 2,
																														z
																																+ -1) == 0) {
																											if (world.getBlockId(x + 2,	y + 2,z+ -2) == 0) {
																												return true;
																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	private static boolean checkEast(World world, int x, int y, int z) {
		if (world.getBlockId(x + 1, y + 0, z + 0) == 55) {
			if (world.getBlockId(x + 2, y + 0, z + 0) == RPGLevel.blockEEOID) {
				if (world.getBlockId(x + 0, y + 1, z + 0) == 55) {
					if (world.getBlockId(x + 1, y + 1, z + 0) == 0) {
						if (world.getBlockId(x + 2, y + 1, z + 0) == 55) {
							if (world.getBlockId(x + 0, y + 2, z + 0) == 0) {
								if (world.getBlockId(x + 1, y + 2, z + 0) == 0) {
									if (world.getBlockId(x + 2, y + 2, z + 0) == 0) {
										if (world.getBlockId(x + 0, y + 0,
												z + 1) == 55) {
											if (world.getBlockId(x + 1, y + 0,
													z + 1) == RPGLevel.blockLUSID) {
												if (world.getBlockId(x + 2,
														y + 0, z + 1) == 55) {
													if (world.getBlockId(x + 0,
															y + 1, z + 1) == 0) {
														if (world.getBlockId(
																x + 1, y + 1,
																z + 1) == 0) {
															if (world
																	.getBlockId(
																			x + 2,
																			y + 1,
																			z + 1) == 0) {
																if (world
																		.getBlockId(
																				x + 0,
																				y + 2,
																				z + 1) == 0) {
																	if (world
																			.getBlockId(
																					x + 1,
																					y + 2,
																					z + 1) == 0) {
																		if (world
																				.getBlockId(
																						x + 2,
																						y + 2,
																						z + 1) == 0) {
																			if (world
																					.getBlockId(
																							x + 0,
																							y + 0,
																							z + 2) == RPGLevel.blockEEOID) {
																				if (world
																						.getBlockId(
																								x + 1,
																								y + 0,
																								z + 2) == 55) {
																					if (world
																							.getBlockId(
																									x + 2,
																									y + 0,
																									z + 2) == RPGLevel.blockEEOID) {
																						if (world
																								.getBlockId(
																										x + 0,
																										y + 1,
																										z + 2) == 55) {
																							if (world
																									.getBlockId(
																											x + 1,
																											y + 1,
																											z + 2) == 0) {
																								if (world
																										.getBlockId(
																												x + 2,
																												y + 1,
																												z + 2) == 55) {
																									if (world
																											.getBlockId(
																													x + 0,
																													y + 2,
																													z + 2) == 0) {
																										if (world
																												.getBlockId(
																														x + 1,
																														y + 2,
																														z + 2) == 0) {
																											if (world
																													.getBlockId(
																															x + 2,
																															y + 2,
																															z + 2) == 0) {
																												return true;
																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	private static boolean checkSouth(World world, int x, int y, int z) {
		if (world.getBlockId(x + 0, y + 0, z + 1) == 55) {
			if (world.getBlockId(x + 0, y + 0, z + 2) == RPGLevel.blockEEOID) {
				if (world.getBlockId(x + 0, y + 1, z + 0) == 55) {
					if (world.getBlockId(x + 0, y + 1, z + 1) == 0) {
						if (world.getBlockId(x + 0, y + 1, z + 2) == 55) {
							if (world.getBlockId(x + 0, y + 2, z + 0) == 0) {
								if (world.getBlockId(x + 0, y + 2, z + 1) == 0) {
									if (world.getBlockId(x + 0, y + 2, z + 2) == 0) {
										if (world.getBlockId(x + -1, y + 0,
												z + 0) == 55) {
											if (world.getBlockId(x + -1, y + 0,
													z + 1) == RPGLevel.blockLUSID) {
												if (world.getBlockId(x + -1,
														y + 0, z + 2) == 55) {
													if (world.getBlockId(
															x + -1, y + 1,
															z + 0) == 0) {
														if (world.getBlockId(x
																+ -1, y + 1,
																z + 1) == 0) {
															if (world
																	.getBlockId(
																			x
																					+ -1,
																			y + 1,
																			z + 2) == 0) {
																if (world
																		.getBlockId(
																				x
																						+ -1,
																				y + 2,
																				z + 0) == 0) {
																	if (world
																			.getBlockId(
																					x
																							+ -1,
																					y + 2,
																					z + 1) == 0) {
																		if (world
																				.getBlockId(
																						x
																								+ -1,
																						y + 2,
																						z + 2) == 0) {
																			if (world
																					.getBlockId(
																							x
																									+ -2,
																							y + 0,
																							z + 0) == RPGLevel.blockEEOID) {
																				if (world
																						.getBlockId(
																								x
																										+ -2,
																								y + 0,
																								z + 1) == 55) {
																					if (world
																							.getBlockId(
																									x
																											+ -2,
																									y + 0,
																									z + 2) == RPGLevel.blockEEOID) {
																						if (world
																								.getBlockId(
																										x
																												+ -2,
																										y + 1,
																										z + 0) == 55) {
																							if (world
																									.getBlockId(
																											x
																													+ -2,
																											y + 1,
																											z + 1) == 0) {
																								if (world
																										.getBlockId(
																												x
																														+ -2,
																												y + 1,
																												z + 2) == 55) {
																									if (world
																											.getBlockId(
																													x
																															+ -2,
																													y + 2,
																													z + 0) == 0) {
																										if (world
																												.getBlockId(
																														x
																																+ -2,
																														y + 2,
																														z + 1) == 0) {
																											if (world
																													.getBlockId(
																															x
																																	+ -2,
																															y + 2,
																															z + 2) == 0) {
																												return true;
																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	private static boolean checkWest(World world, int x, int y, int z) {
		if (world.getBlockId(x + -1, y + 0, z + 0) == 55) {
			if (world.getBlockId(x + -2, y + 0, z + 0) == RPGLevel.blockEEOID) {
				if (world.getBlockId(x + 0, y + 1, z + 0) == 55) {
					if (world.getBlockId(x + -1, y + 1, z + 0) == 0) {
						if (world.getBlockId(x + -2, y + 1, z + 0) == 55) {
							if (world.getBlockId(x + 0, y + 2, z + 0) == 0) {
								if (world.getBlockId(x + -1, y + 2, z + 0) == 0) {
									if (world.getBlockId(x + -2, y + 2, z + 0) == 0) {
										if (world.getBlockId(x + 0, y + 0, z
												+ -1) == 55) {
											if (world.getBlockId(x + -1, y + 0,
													z + -1) == RPGLevel.blockLUSID) {
												if (world.getBlockId(x + -2,
														y + 0, z + -1) == 55) {
													if (world.getBlockId(x + 0,
															y + 1, z + -1) == 0) {
														if (world.getBlockId(x
																+ -1, y + 1, z
																+ -1) == 0) {
															if (world
																	.getBlockId(
																			x
																					+ -2,
																			y + 1,
																			z
																					+ -1) == 0) {
																if (world
																		.getBlockId(
																				x + 0,
																				y + 2,
																				z
																						+ -1) == 0) {
																	if (world
																			.getBlockId(
																					x
																							+ -1,
																					y + 2,
																					z
																							+ -1) == 0) {
																		if (world
																				.getBlockId(
																						x
																								+ -2,
																						y + 2,
																						z
																								+ -1) == 0) {
																			if (world
																					.getBlockId(
																							x + 0,
																							y + 0,
																							z
																									+ -2) == RPGLevel.blockEEOID) {
																				if (world
																						.getBlockId(
																								x
																										+ -1,
																								y + 0,
																								z
																										+ -2) == 55) {
																					if (world
																							.getBlockId(
																									x
																											+ -2,
																									y + 0,
																									z
																											+ -2) == RPGLevel.blockEEOID) {
																						if (world
																								.getBlockId(
																										x + 0,
																										y + 1,
																										z
																												+ -2) == 55) {
																							if (world
																									.getBlockId(
																											x
																													+ -1,
																											y + 1,
																											z
																													+ -2) == 0) {
																								if (world
																										.getBlockId(
																												x
																														+ -2,
																												y + 1,
																												z
																														+ -2) == 55) {
																									if (world
																											.getBlockId(
																													x + 0,
																													y + 2,
																													z
																															+ -2) == 0) {
																										if (world
																												.getBlockId(
																														x
																																+ -1,
																														y + 2,
																														z
																																+ -2) == 0) {
																											if (world
																													.getBlockId(
																															x
																																	+ -2,
																															y + 2,
																															z
																																	+ -2) == 0) {
																												return true;
																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

}