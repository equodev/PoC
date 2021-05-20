package com.equo.poc;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.osgi.service.component.annotations.Component;

import com.equo.application.api.IEquoApplication;
import com.equo.application.model.EquoApplicationBuilder;
import com.equo.poc.eventhandlers.ExitAppHandler;

@Component
public class EquoApp implements IEquoApplication {

	@Override
	public EquoApplicationBuilder buildApp(EquoApplicationBuilder appBuilder) {
		return appBuilder.withUI("index.html").beforeExit(() -> {
			return new ExitAppHandler().showDialog() == IDialogConstants.OK_ID;
		}).start();
	}

}
