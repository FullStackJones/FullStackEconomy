package net.fullstackjones.fullstackeconomy.services;

import net.fullstackjones.fullstackeconomy.FullStackEconomy;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResourceHelper {
    public static ResourceLocation[] getCurrencySymbolResourceLocation(String namespace, String folderPath) {
        List<ResourceLocation> resourceLocations = new ArrayList<>();
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();

        try {
            // Get all resources in the folder
            Map<ResourceLocation, ?> resources = resourceManager.listResources(folderPath, path -> {
                System.out.println("Checking path: " + path.getPath());
                return path.getPath().endsWith(".png");
            });
            for (ResourceLocation resource : resources.keySet()) {
                if (resource.getNamespace().equals(namespace)) {
                    // Adjust the path to remove the prefix and suffix
                    String adjustedPath = resource.getPath()
                            .replace("textures/gui/sprites/", "") // Remove the prefix
                            .replace(".png", "");                // Remove the .png extension
                    resourceLocations.add(ResourceLocation.fromNamespaceAndPath(FullStackEconomy.MODID, adjustedPath));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resourceLocations.toArray(new ResourceLocation[0]);
    }
}
