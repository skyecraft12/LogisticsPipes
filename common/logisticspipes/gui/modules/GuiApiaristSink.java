package logisticspipes.gui.modules;


import logisticspipes.modules.ModuleApiaristSink;
import logisticspipes.modules.ModuleApiaristSink.FilterType;
import logisticspipes.modules.ModuleApiaristSink.SinkSetting;
import logisticspipes.network.PacketHandler;
import logisticspipes.network.packets.module.BeeModuleSetBeePacket;
import logisticspipes.proxy.MainProxy;
import logisticspipes.proxy.SimpleServiceLocator;
import logisticspipes.utils.gui.BasicGuiHelper;
import logisticspipes.utils.gui.DummyContainer;
import logisticspipes.utils.gui.IItemTextureRenderSlot;
import logisticspipes.utils.gui.ISmallColorRenderSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GuiApiaristSink extends ModuleBaseGui {

	private final ModuleApiaristSink module;
	
	public GuiApiaristSink(ModuleApiaristSink module, EntityPlayer player) {
		super(new DummyContainer(player.inventory,null), module);
		this.module = module;
		for(int i=0; i < 6; i++) {
			SinkSetting filter = module.filter[i];
			this.addRenderSlot(new TypeSlot(20, 20 + (i*18), filter, i));
			this.addRenderSlot(new GroupSlot(guiLeft + 45, guiTop + 25 + (i*18), filter, i));
			this.addRenderSlot(new BeeSlot(60, 20 + (i*18),filter,0, i));
			this.addRenderSlot(new BeeSlot(78, 20 + (i*18),filter,1, i));
		}
		xSize = 120;
		ySize = 150;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		BasicGuiHelper.drawGuiBackGround(mc, guiLeft, guiTop, right, bottom, zLevel, true);
	}
	
	private class TypeSlot extends IItemTextureRenderSlot {

		final private int xPos;
		final private int yPos;
		final private SinkSetting setting;
		final private int row;
		
		private TypeSlot(int xPos, int yPos, SinkSetting setting, int row) {
			this.xPos = xPos;
			this.yPos = yPos;
			this.setting = setting;
			this.row = row;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public IIcon getTextureIcon() {
			if(setting.filterType == null) return null;
			return SimpleServiceLocator.forestryProxy.getIconFromTextureManager("analyzer/" + setting.filterType.icon);
		}

		@Override
		public void mouseClicked(int button) {
			if(button == 2) {
				setting.FilterTypeReset();
			}
			if(button == 0) {
				setting.FilterTypeUp();
			}
			if(button == 1) {
				setting.FilterTypeDown();
			}
			MainProxy.sendPacketToServer(PacketHandler.getPacket(BeeModuleSetBeePacket.class).setInteger2(row).setInteger3(3).setInteger4(setting.filterType.ordinal()).setModulePos(module));
		}

		@Override
		public boolean drawSlotBackground() {
			return true;
		}

		@Override
		public int getXPos() {
			return xPos;
		}

		@Override
		public int getYPos() {
			return yPos;
		}

		@Override
		public boolean drawSlotIcon() {
			return true;
		}

		@Override
		public String getToolTipText() {
			if(setting.filterType == null) return "";
			return SimpleServiceLocator.forestryProxy.getForestryTranslation(setting.filterType.path);
		}

		@Override
		public boolean displayToolTip() {
			return setting.filterType != FilterType.Null;
		}

		@Override
		public boolean customRender(Minecraft mc, float zLevel) {
			return false;
		}
		
	}
	private class GroupSlot extends ISmallColorRenderSlot {

		final private int xPos;
		final private int yPos;
		final private SinkSetting setting;
		final private int row;

		private GroupSlot(int xPos, int yPos, SinkSetting setting, int row) {
			this.xPos = xPos;
			this.yPos = yPos;
			this.setting = setting;
			this.row = row;
		}
		
		@Override
		public void mouseClicked(int button) {
			if(button == 2) {
				setting.filterGroupReset();
			}
			if(button == 0) {
				setting.filterGroupUp();
			}
			if(button == 1) {
				setting.filterGroupDown();
			}
			MainProxy.sendPacketToServer(PacketHandler.getPacket(BeeModuleSetBeePacket.class).setInteger2(row).setInteger3(2).setInteger4(setting.filterGroup).setModulePos(module));
		}

		@Override
		public boolean drawSlotBackground() {
			return setting.filterType != FilterType.Null;
		}

		@Override
		public int getXPos() {
			return xPos;
		}

		@Override
		public int getYPos() {
			return yPos;
		}

		@Override
		public String getToolTipText() {
			switch(setting.filterGroup) {
			case 1:
				return "GroupColor: Red";
			case 2:
				return "GroupColor: Green";
			case 3:
				return "GroupColor: Blue";
			case 4:
				return "GroupColor: Yellow";
			case 5:
				return "GroupColor: Cyan";
			case 6:
				return "GroupColor: Purple";
			default:
				return "No Group";
			}
		}

		@Override
		public boolean displayToolTip() {
			return drawSlotBackground();
		}

		@Override
		public int getColor() {
			switch(setting.filterGroup) {
			case 1:
				return 0xFFFF0000;
			case 2:
				return 0xFF00FF00;
			case 3:
				return 0xFF0000FF;
			case 4:
				return 0xFFFFFF00;
			case 5:
				return 0xFF00FFFF;
			case 6:
				return 0xFFFF00FF;
			default:
				return 0;
			}
		}

		@Override
		public boolean drawColor() {
			return drawSlotBackground();
		}
		
	}
	private class BeeSlot extends IItemTextureRenderSlot {

		final private int xPos;
		final private int yPos;
		final private SinkSetting setting;
		final private int slotNumber;
		final private int row;
		
		private BeeSlot(int xPos, int yPos, SinkSetting setting, int slotNumber, int row) {
			this.xPos = xPos;
			this.yPos = yPos;
			this.setting = setting;
			this.slotNumber = slotNumber;
			this.row = row;
		}

		@Override
		public void mouseClicked(int button) {
			if(button == 2) {
				if(slotNumber == 0) {
					setting.firstBeeReset();
				} else {
					setting.secondBeeReset();
				}
			}
			if(button == 0) {
				if(slotNumber == 0) {
					setting.firstBeeUp();
				} else {
					setting.secondBeeUp();
				}
			}
			if(button == 1) {
				if(slotNumber == 0) {
					setting.firstBeeDown();
				} else {
					setting.secondBeeDown();
				}
			}
			MainProxy.sendPacketToServer(PacketHandler.getPacket(BeeModuleSetBeePacket.class).setInteger2(row).setInteger3(slotNumber).setString1(slotNumber == 0 ? setting.firstBee : setting.secondBee).setModulePos(module));
		}

		@Override
		public boolean drawSlotBackground() {
			return setting.filterType.secondSlots > slotNumber;
		}

		@Override
		public int getXPos() {
			return xPos;
		}

		@Override
		public int getYPos() {
			return yPos;
		}

		@Override
		public boolean drawSlotIcon() {
			return drawSlotBackground() && !(slotNumber == 0 ? setting.firstBee : setting.secondBee).isEmpty();
		}

		@Override
		public String getToolTipText() {
			return SimpleServiceLocator.forestryProxy.getAlleleName(slotNumber == 0 ? setting.firstBee : setting.secondBee);
		}

		@Override
		public boolean displayToolTip() {
			if(slotNumber == 0) {
				return !setting.firstBee.isEmpty() && drawSlotBackground();
			} else {
				return !setting.secondBee.isEmpty() && drawSlotBackground();
			}
		}

		@Override
		public boolean customRender(Minecraft mc, float zLevel) {
			if(slotNumber == 0) {
				BasicGuiHelper.renderForestryBeeAt(mc, xPos + 1, yPos + 1, zLevel, setting.firstBee);
			} else {
				BasicGuiHelper.renderForestryBeeAt(mc, xPos + 1, yPos + 1, zLevel, setting.secondBee);
			}
			return true;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public IIcon getTextureIcon() {
			return null;
		}
		
	}
}
