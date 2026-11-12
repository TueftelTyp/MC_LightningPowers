package de.mcrising.lightningpowers.client;

import de.mcrising.lightningpowers.LightningPowers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;

/** Client-only, head-anchored 3D armor renderer. */
public final class LightningPowersClient implements ClientModInitializer {
    private static final Identifier NORMAL_TEXTURE = Identifier.parse("lightningpowers:textures/entity/helmet.png");
    private static final Identifier CHARGED_TEXTURE = Identifier.parse("lightningpowers:textures/entity/helmet_charged.png");
    private static final Identifier CHARGED_ITEM_MODEL = Identifier.parse("lightningpowers:lightning_helmet_charged");

    @Override
    public void onInitializeClient() {
        final HumanoidModel<net.minecraft.client.renderer.entity.state.HumanoidRenderState> helmetModel = createHelmetModel();
        ArmorRenderer.register((pose, collector, itemStack, state, slot, light, contextModel) -> {
            if (slot != EquipmentSlot.HEAD) return;
            // The vanilla humanoid pose is applied by the collector via setupAnim(state).
            // All visible cubes are children of the head bone and follow head rotations.
            Identifier texture = CHARGED_ITEM_MODEL.equals(itemStack.get(DataComponents.ITEM_MODEL))
                    ? CHARGED_TEXTURE : NORMAL_TEXTURE;
            collector.submitModel(helmetModel, state, pose, texture,
                    light, OverlayTexture.NO_OVERLAY, state.outlineColor);
        }, LightningPowers.HELMET);
    }

    private static HumanoidModel<net.minecraft.client.renderer.entity.state.HumanoidRenderState> createHelmetModel() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition head = mesh.getRoot().addOrReplaceChild("head", CubeListBuilder.create()
                // Copper crown and front brow. Face stays visible.
                .texOffs(0, 0).addBox(-4.6F, -8.8F, -4.6F, 9.2F, 2.2F, 9.2F)
                .texOffs(0, 15).addBox(-4.6F, -6.8F, -4.7F, 9.2F, 1.3F, 1.25F)
                // Metallic left and right side guards.
                .texOffs(0, 22).addBox(-4.8F, -6.6F, -4.2F, 1.4F, 5.2F, 8.4F)
                .texOffs(0, 22).addBox(3.4F, -6.6F, -4.2F, 1.4F, 5.2F, 8.4F)
                // Back of the helmet.
                .texOffs(0, 36).addBox(-4.4F, -6.5F, 3.4F, 8.8F, 5.1F, 1.2F), PartPose.ZERO);
        // Keep the child expected by HumanoidModel; disable the vanilla skin hat cube.
        head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        // A 3D lightning rod, attached to the crown instead of floating in world space.
        head.addOrReplaceChild("rod_base", CubeListBuilder.create()
                .texOffs(38, 0).addBox(-1.6F, -10.0F, -1.6F, 3.2F, 1.3F, 3.2F), PartPose.ZERO);
        head.addOrReplaceChild("rod_shaft", CubeListBuilder.create()
                .texOffs(39, 12).addBox(-0.65F, -14.0F, -0.65F, 1.3F, 4.0F, 1.3F), PartPose.ZERO);
        head.addOrReplaceChild("rod_tip", CubeListBuilder.create()
                .texOffs(38, 19).addBox(-1.15F, -15.5F, -1.15F, 2.3F, 1.6F, 2.3F), PartPose.ZERO);
        // Blue energy strips physically inset over the forehead and cheek guards.
        head.addOrReplaceChild("energy_brow", CubeListBuilder.create()
                .texOffs(0, 46).addBox(-3.4F, -6.1F, -4.82F, 6.8F, 0.75F, 0.3F), PartPose.ZERO);
        head.addOrReplaceChild("energy_sides", CubeListBuilder.create()
                .texOffs(0, 50).addBox(-4.93F, -5.3F, -2.6F, 0.3F, 2.6F, 3.8F)
                .texOffs(0, 50).addBox(4.63F, -5.3F, -2.6F, 0.3F, 2.6F, 3.8F), PartPose.ZERO);
        HumanoidModel<net.minecraft.client.renderer.entity.state.HumanoidRenderState> model =
                new HumanoidModel<>(LayerDefinition.create(mesh, 64, 64).bakeRoot());
        // Only the modeled head is submitted by the custom armor renderer.
        model.body.visible = false;
        model.leftArm.visible = false;
        model.rightArm.visible = false;
        model.leftLeg.visible = false;
        model.rightLeg.visible = false;
        model.hat.visible = false;
        return model;
    }
}
