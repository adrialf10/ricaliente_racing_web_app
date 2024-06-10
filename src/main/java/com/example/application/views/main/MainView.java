package com.example.application.views.main;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
		generateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		generateButton.addClickListener(event -> {
			String kartNumbers = kartsInput.getValue();
			Double boxLimit = boxInput.getValue();

			if(kartsInput.isEmpty() && boxInput.isEmpty()) {
				Notification notification = new Notification();
				notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
				notification.setDuration(5000);

				Button trollButton = new Button("Completar");
				trollButton.addClickListener(complete -> {
					Notification notification2 = Notification.show("Qué quieres, ¿que haga todo por ti? Trabaja un poco que no haces nada.");
					notification2.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
				});

				HorizontalLayout layout = new HorizontalLayout(new Text("Tu lo que eres es bobo, rellena los campos anda."), trollButton);
				layout.setAlignItems(Alignment.CENTER);
				notification.add(layout);

				notification.open();
			}
			else if(kartsInput.isEmpty()) {
				Notification notification = Notification.show("Qué pasa, ¿nadie quiere correr contigo?");
				notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
			}
			else if(boxInput.isEmpty()) {
				Notification notification = Notification.show("Te sorprendería saber que en los boxes hay karts.");
				notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
			}
			else if(!checkListInput(kartNumbers)) {
				Notification notification = Notification.show("Maaan, solo tenías un trabajo, pon letras o números naturales separados por comas y sin espacios.");
				notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
			} 
			else if(!isInteger(boxLimit)) {
				Notification notification = Notification.show("Un número decimal... Cuidado no se vaya a partir el kart cuando te comas el muro.");
				notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
			}
			else if(boxLimit < 0) {
				Notification notification = Notification.show("De verdad, ¿un número negativo?, tú solo quieres tocar los huevos.");
				notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
			} else {
				String paramaters = kartNumbers + ";" + boxLimit.intValue();

				getUI().ifPresent(ui -> ui.navigate(RaceView.class, paramaters)); 
			}  
		});

		Button helpButton = new Button(new Icon(VaadinIcon.QUESTION));
		setHorizontalComponentAlignment(Alignment.END, helpButton);

		helpButton.addClickListener(random_event -> {
			String[] options = {
					"Jeje, god cabrón.",
					"Sí, me he pasado una hora programando esta mierda solo para vacilarte.",
					"Eyeyey pequeña, si quieres te enseño a leer.",
					"De verdad necesitas ayuda con esto... ¿Que tienes, 5 años?",
					"Si no sabes leer, no me quiero ni imaginar como conduces."         
			};
			double[] percentages = {
					0.03,
					0.15,
					0.10,
					0.36,
					0.36
			};

			double randomNumber = Math.random();
			double cumulativePercentage = 0.0;

			for (int i = 0; i < options.length; i++) {
				cumulativePercentage += percentages[i];
				if (randomNumber <= cumulativePercentage) {
					Notification notification = Notification.show(options[i]);
					notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
					break;
				}
			}
		});

		setJustifyContentMode(JustifyContentMode.CENTER);

		setAlignItems(Alignment.CENTER);

		add(helpButton, image, kartsInput, boxInput, generateButton);
	}

	private boolean checkListInput(String list) {
		String regex = "^[a-zA-Z0-9]+(,[a-zA-Z0-9]+)*$";

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(list);

		return matcher.matches();
	}

	private boolean isInteger(Double input) {
		return (input % 1) == 0;
	}
}