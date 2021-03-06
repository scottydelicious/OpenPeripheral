package openperipheral.converter;

import java.util.Map;

import net.minecraft.item.ItemStack;
import openperipheral.api.ITypeConverter;
import openperipheral.util.InventoryDescriptionUtils;

public class ConverterItemStack implements ITypeConverter {

	@Override
	public Object fromLua(Object o, Class<?> required) {
		if (required == ItemStack.class && o instanceof Map) {
			int quantity = 1;
			int dmg = 0;

			@SuppressWarnings({ "unchecked", "rawtypes" })
			Map<Object, Object> m = (Map)o;

			if (!m.containsKey("id")) { return null; }
			int id = (int)(double)(Double)m.get("id");
			if (m.containsKey("qty")) {
				quantity = (int)(double)(Double)m.get("qty");
			}
			if (m.containsKey("dmg")) {
				dmg = (int)(double)(Double)m.get("dmg");
			}
			return new ItemStack(id, quantity, dmg);
		}
		return null;
	}

	@Override
	public Object toLua(Object o) {
		if (o instanceof ItemStack) { return InventoryDescriptionUtils.itemstackToMap((ItemStack)o); }
		return null;
	}

}
