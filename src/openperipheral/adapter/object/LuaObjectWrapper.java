package openperipheral.adapter.object;

import openperipheral.adapter.AdaptedClass;
import openperipheral.adapter.AdapterManager;

import com.google.common.base.Preconditions;

import dan200.computer.api.ILuaContext;
import dan200.computer.api.ILuaObject;

public class LuaObjectWrapper {
	public static ILuaObject wrap(AdapterManager<?, IObjectMethodExecutor> manager, Object target) {
		Preconditions.checkNotNull(target, "Can't wrap null");
		AdaptedClass<IObjectMethodExecutor> adapted = manager.getAdapterClass(target.getClass());
		return wrap(adapted, target);
	}

	public static ILuaObject wrap(final AdaptedClass<IObjectMethodExecutor> adapted, final Object target) {
		return new ILuaObject() {

			@Override
			public String[] getMethodNames() {
				return adapted.methodNames;
			}

			@Override
			public Object[] callMethod(ILuaContext context, int method, Object[] arguments) throws Exception {
				IObjectMethodExecutor executor = adapted.getMethod(method);
				Preconditions.checkNotNull(executor, "Invalid method index %s for wrapped %s", method, target.getClass());
				return executor.execute(context, target, arguments);
			}
		};
	}
}
