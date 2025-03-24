package net.fullstackjones.fullstackeconomy.data;

import net.minecraft.nbt.CompoundTag;

public class CurrencyItemData {
    private final String _Size;
    private final String _Shape;
    private final String _Accent;
    private final int _Value;
    private final String _Name;

    public CurrencyItemData(String size, String shape, String accent, int value, String name){
        _Size = size;
        _Shape = shape;
        _Accent = accent;
        _Value = value;
        _Name = name;
    }

    public CurrencyItemData(CurrencyItemData currency, String name, int value){
        _Size = currency.GetSize();
        _Shape = currency.GetShape();
        _Accent = currency.GetAccent();
        _Value = value;
        _Name = name;
    }

    public String GetSize(){
        return _Size;
    }

    public String GetShape(){
        return _Shape;
    }

    public String GetAccent(){
        return _Accent;
    }

    public String GetName(){
        return _Name;
    }

    public int GetValue(){
        return _Value;
    }

    public void put(CompoundTag tag) {
        tag.putString("Size", _Size);
        tag.putString("Shape", _Shape);
        tag.putString("Accent", _Accent);
        tag.putInt("Value", _Value);
        tag.putString("Name", _Name);
    }

    public static CurrencyItemData load(CompoundTag tag) {
        String size = tag.getString("Size");
        String shape = tag.getString("Shape");
        String accent = tag.getString("Accent");
        int value = tag.getInt("Value");
        String name = tag.getString("Name");
        return new CurrencyItemData(size, shape, accent, value, name);
    }
}
