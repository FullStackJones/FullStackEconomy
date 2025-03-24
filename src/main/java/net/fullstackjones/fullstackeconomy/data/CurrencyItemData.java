package net.fullstackjones.fullstackeconomy.data;

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
}
