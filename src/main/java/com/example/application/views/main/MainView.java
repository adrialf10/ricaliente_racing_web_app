package com.example.application.views.main;

import java.util.Stack;

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
	
	public MainView() {
		setWidth("100%");
		setHeight("100%");

		TextField kartsInput = new TextField("Enter the numbers of the karts (comma-separated)");
		
		TextField boxRowInput = new TextField("Enter the number of rows in box");
		TextField boxKartsInput = new TextField("Enter the number of karts per row in box");
		
		Button generateButton = new Button("Generate Race");

		generateButton.addClickListener(input_kart_numbers -> {
			String input = kartsInput.getValue();
			String[] kartsList = input.split(",");
			Stack boxes = new Stack();
			
			int remainingButtons = kartsList.length;

	        while (remainingButtons > 0) {
				VerticalLayout layout = createKartsVerticalLayout(kartsList, remainingButtons);
	
				add(layout);
				
				remainingButtons -= layout.getChildren().count();
	        }
	        
			//VerticalLayout layout = createKartsVerticalLayout(kartsList, kartsList.length);
			
			//add(layout);
		});

		add(kartsInput, generateButton);
	}
	
	private VerticalLayout createKartsVerticalLayout(String[] kartsList, int kartsNumber) {
		VerticalLayout layout = new VerticalLayout();
		layout.setWidth("100%");
		layout.setHeight("100%");
		
		int buttonsToAdd = calculateButtonsToAdd(layout, kartsNumber);
		
		for (int i = 0; i < buttonsToAdd; i++) {
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
		
		UI.getCurrent().getPage().retrieveExtendedClientDetails(details -> {
            System.out.println("Width " + details.getWindowInnerWidth());
            System.out.println("Height " + details.getWindowInnerHeight());
        });
		
		return button;
	}

	private int calculateButtonsToAdd(VerticalLayout layout, int remainingButtons) {
		int buttonsPerLayout = 0;
		int buttonHeight = 100; // Height of each button in pixels (adjust as needed)
		
		int layoutHeight = getBrowserDimension();// Get the height of the browser window

		while (buttonsPerLayout * buttonHeight < layoutHeight && buttonsPerLayout < remainingButtons) {
			buttonsPerLayout++;
		}

		return buttonsPerLayout;
	}
	
	private int getBrowserDimension() {
		UI.getCurrent().getPage().retrieveExtendedClientDetails(evt -> {
			final int height = evt.getWindowInnerHeight();
			
		});
		return 911;
	}
}