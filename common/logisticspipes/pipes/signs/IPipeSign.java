package logisticspipes.pipes.signs;

import logisticspipes.network.abstractpackets.ModernPacket;
import logisticspipes.pipes.basic.CoreRoutedPipe;
import logisticspipes.renderer.LogisticsRenderPipe;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IPipeSign {
	// Methods used when assigning a sign
	public boolean isAllowedFor(CoreRoutedPipe pipe);
	public void addSignTo(CoreRoutedPipe pipe, ForgeDirection dir, EntityPlayer player);
	// For Final Pipe
	public void readFromNBT(NBTTagCompound tag);
	public void writeToNBT(NBTTagCompound tag);
	public void init(CoreRoutedPipe pipe, ForgeDirection dir);
	public void activate(EntityPlayer player);
	public ModernPacket getPacket();
	public void updateServerSide();
	@SideOnly(Side.CLIENT)
	public void renderReal(CoreRoutedPipe pipe, LogisticsRenderPipe renderer);
	@SideOnly(Side.CLIENT)
	public Framebuffer getFrameBuffer(int width, int height);
	public boolean needsBufferRefresh();
	public void renderBuffer(CoreRoutedPipe pipe, LogisticsRenderPipe renderer);
}
