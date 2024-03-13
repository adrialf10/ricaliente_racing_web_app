package com.example.application.views.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Ricaliente Racing") 
@Route("")
public class MainView extends VerticalLayout { 

	public MainView() {
		Image image = new Image("images/RicalienteRacing.png", "Ricaliente Racing");
        image.setWidth("250px");
        
		TextField kartsInput = new TextField("Introduce los números de los karts separados por comas");
		kartsInput.setWidth("400px");
		
		kartsInput.getStyle().set("text-align", "center");
		NumberField boxInput = new NumberField("Introduce el número de karts por box");
		boxInput.setWidth("400px");
		
		Button generateButton = new Button("Iniciar la carrera");
        
		generateButton.addClickListener(event -> {
			String kartNumbers = kartsInput.getValue();
			Double boxLimit = boxInput.getValue();
			
			if(kartsInput.isEmpty() && boxInput.isEmpty()) {
				Notification.show("Tu lo que eres es bobo, rellena los campos anda.");
			}
			else if(kartsInput.isEmpty()) {
				Notification.show("Qué pasa, ¿nadie quiere correr contigo?");
			}
			else if(boxInput.isEmpty()) {
				Notification.show("Te sorprendería saber que en los boxes hay karts.");
			}
			else if(!checkListInput(kartNumbers)) {
				Notification.show("Maaan, tenías un trabajo, solo números naturales separados por comas y sin espacios.");
			} 
			else if(!isInteger(boxLimit)) {
				Notification.show("Un número decimal... Cuidado no se vaya a partir el kart cuando te comas el muro.");
			}
			else if(boxLimit < 0) {
				Notification.show("De verdad, ¿un número negativo?, tú solo quieres tocar los huevos.");
			} else {
				String paramaters = kartNumbers + ";" + boxLimit.intValue();
	            
	            getUI().ifPresent(ui -> ui.navigate(RaceView.class, paramaters)); 
			}  
        });
		
		setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        
        add(image, kartsInput, boxInput, generateButton);
    }
	
	private boolean checkListInput(String list) {
		String regex = "^\\d+(,\\d+)*$";
		
        Pattern pattern = Pattern.compile(regex);
        
        Matcher matcher = pattern.matcher(list);
        
        return matcher.matches();
	}
	
	private boolean isInteger(Double input) {
		return (input % 1) == 0;
    }
}