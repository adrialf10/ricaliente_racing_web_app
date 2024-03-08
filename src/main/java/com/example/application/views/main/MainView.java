package com.example.application.views.main;

import java.util.List;

import com.example.application.data.entity.Kart;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.Dimension;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@PageTitle("Ricaliente Racing") 
@Route("") 
public class MainView extends HorizontalLayout { 
	
	private final int KARTS_PER_COLUMN = 5;
	
	public MainView() {
		setWidth("100%");
		setHeight("100%");
		
		TextField numberField = new TextField("Enter a list of numbers (comma-separated):");
		Button generateButton = new Button("Generate Buttons");
		
		generateButton.addClickListener(input_kart_numbers -> {
			String input = numberField.getValue();
			String[] kartsList = input.split(",");
			
			List<Kart> raceKarts = ;
			Stack>
			
			int totalKarts = kartsList.length;
	        int layoutCount = (int) Math.ceil((double) totalKarts / KARTS_PER_COLUMN);
	        
			for (int i = 0; i < layoutCount; i++) {
				VerticalLayout layout = createKartsVerticalLayout(kartsList, i);
				add(layout);
			}
		});

		add(numberField, generateButton);
	}
	
	private VerticalLayout createKartsVerticalLayout(String[] kartsList, int layoutIndex) {
		VerticalLayout layout = new VerticalLayout();
		layout.setWidth("100%");
		layout.setHeight("100%");
		
		for (int i = layoutIndex * KARTS_PER_COLUMN; i < Math.min((layoutIndex + 1) * KARTS_PER_COLUMN, kartsList.length); i++) {
			Button button = createKartsButtons(kartsList[i]);

			layout.add(button);
		}

		return layout;
	}

	private Button createKartsButtons(String kartsNumber) {
		Button button = new Button("Kart " + kartsNumber);
		button.addClassName("no_info");

		ContextMenu contextMenu = new ContextMenu();
		contextMenu.setOpenOnClick(true);
		contextMenu.setTarget(button);

		contextMenu.addItem("no info", color_event -> button.setClassName("no_info"));
		contextMenu.addItem("fast", color_event -> button.setClassName("fast"));
		contextMenu.addItem("slow", color_event -> button.setClassName("slow"));
		
		return button;
	}
}