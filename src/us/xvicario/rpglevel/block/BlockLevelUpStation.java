package us.xvicario.rpglevel.block;

import java.util.Random;

import us.xvicario.rpglevel.RPGLevel;
import us.xvicario.rpglevel.tileentity.TileEntityLevelUpStation;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.FMLNetworkHandler;
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
			if (player.getHeldItem() != null) {
				if (player.getHeldItem().getItem() == Item.pickaxeIron) {
					player.destroyCurrentEquippedItem();
					for (int i = 0; i < 20; i++) {
						player.addExperienceLevel(-1);
					}
					player.addChatMessage("You had an Iron Pickaxe!");
				}
			}
		}
		return true;
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