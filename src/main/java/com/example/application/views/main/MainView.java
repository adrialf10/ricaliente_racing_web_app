package com.example.application.views.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.example.application.data.entity.Kart;
import com.example.application.data.entity.Kart.SpeedType;
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
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@PageTitle("Ricaliente Racing") 
@Route("")
public class MainView extends VerticalLayout { 
	
	public MainView() {
		Image image = new Image("images/RicalienteRacing.png", "Ricaliente Racing");
        image.setWidth("250px"); // Set the width of the image
        
		TextField kartsInput = new TextField("Introduce los números de los karts separados por comas");
		kartsInput.setWidth("400px");
		
		kartsInput.getStyle().set("text-align", "center");
		NumberField boxInput = new NumberField("Introduce el número de karts por box");
		boxInput.setWidth("400px");
		
		Button generateButton = new Button("Iniciar la carrera");
        
		generateButton.addClickListener(event -> {
			String kartNumbers = kartsInput.getValue();
			Double boxLimit = boxInput.getValue();
			
            String paramaters = kartNumbers + ";" + boxLimit;
            
            getUI().ifPresent(ui -> ui.navigate(RaceView.class, paramaters)); 
        });
		
		setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        
        add(image, kartsInput, boxInput, generateButton);
    }
}