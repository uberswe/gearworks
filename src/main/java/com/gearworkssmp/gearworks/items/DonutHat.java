package com.gearworkssmp.gearworks.items;

import java.util.UUID;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.Nullable;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.client.TrinketRenderer;

public class DonutHat extends TrinketItem implements TrinketRenderer {
	public DonutHat(Properties settings) {
		super(settings);
	}

	public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		return super.getModifiers(stack, slot, entity, uuid);
	}

	@Override
	public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (entity instanceof AbstractClientPlayerEntity player) {
			ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
			TrinketRenderer.followBodyRotations(entity, (BipedEntityModel<LivingEntity>) contextModel);
			TrinketRenderer.translateToFace(matrices, (PlayerEntityModel<AbstractClientPlayerEntity>) contextModel, player, headYaw, headPitch);
			matrices.scale(-0.625f,-0.625f,0.625f);
			matrices.translate(0f,0.1f,0.45f);
			itemRenderer.renderItem(entity, stack, ModelTransformationMode.HEAD, false, matrices, vertexConsumers, entity.getWorld(), light, OverlayTexture.DEFAULT_UV, 0);
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("item.gearworks.donut_hat.tooltip"));
		super.appendTooltip(stack, world, tooltip, context);
	}

	@Override
	public void render(ItemStack itemStack, SlotReference slotReference, EntityModel<? extends LivingEntity> entityModel, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, LivingEntity livingEntity, float v, float v1, float v2, float v3, float v4, float v5) {
		if (entity instanceof AbstractClientPlayerEntity player) {
			ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
			TrinketRenderer.followBodyRotations(entity, (BipedEntityModel<LivingEntity>) contextModel);
			TrinketRenderer.translateToFace(matrices, (PlayerEntityModel<AbstractClientPlayerEntity>) contextModel, player, headYaw, headPitch);
			matrices.scale(-0.625f,-0.625f,0.625f);
			matrices.translate(0f,0.1f,0.45f);
			itemRenderer.renderItem(entity, stack, ModelTransformationMode.HEAD, false, matrices, vertexConsumers, entity.getWorld(), light, OverlayTexture.DEFAULT_UV, 0);
		}
	}
}
