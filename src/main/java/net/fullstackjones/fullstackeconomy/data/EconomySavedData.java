package net.fullstackjones.fullstackeconomy.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EconomySavedData extends SavedData {
    public List<CurrencyData> currencyDataList;

    private EconomySavedData(){
        currencyDataList = new ArrayList<>();
    }

    public static EconomySavedData create() {
        return new EconomySavedData();
    }

    public void RemoveCurrency(CurrencyData data) {
        currencyDataList.remove(data);
        this.setDirty();
    }

    public void AddCurrency(CurrencyData data) {
        currencyDataList.add(data);
        this.setDirty();
    }

    public static EconomySavedData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        EconomySavedData data = EconomySavedData.create();
        ListTag listTag = tag.getList("CurrencyDataList", 10);
        for (int i = 0; i < listTag.size(); i++) {
            data.currencyDataList.add(CurrencyData.load(listTag.getCompound(i)));
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        ListTag listTag = new ListTag();
        for (CurrencyData currencyData : currencyDataList) {
            listTag.add(currencyData.save());
        }
        compoundTag.put("CurrencyDataList", listTag);
        return compoundTag;
    }
}
