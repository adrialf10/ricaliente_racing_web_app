package com.example.application.views.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@PageTitle("Ricaliente Racing") 
@Route("Race") 
public class RaceView extends HorizontalLayout { 
	
	private final int KARTS_PER_COLUMN = 5;
	
	List<Kart> kartsLineup = new ArrayList<Kart>();
	LinkedList<Kart> leftBox = new LinkedList<Kart>();
	LinkedList<Kart> rightBox = new LinkedList<Kart>();
    
    VerticalLayout leftBoxLayout = null;
    VerticalLayout rightBoxLayout = null;	
    
    Double boxLimit = 0.0;
    
    
	public RaceView() {		
		TextField kartsInput = new TextField("Enter the numbers of the karts (comma-separated)");
		NumberField boxInput = new NumberField("Enter the number of karts per row in box");
		Button generateButton = new Button("Start Race");
		
        add(kartsInput, boxInput, generateButton);
		
		generateButton.addClickListener(start_race -> {
			// Create karts lineup			
			String[] kartNumbers = kartsInput.getValue().split(",");
					
	        for (String number : kartNumbers) {
	        	kartsLineup.add(new Kart(number));
	        }
	        
	        int lineuplayoutCount = (int) Math.ceil((double) kartsLineup.size() / KARTS_PER_COLUMN);
			for (int i = 0; i < lineuplayoutCount; i++) {
				VerticalLayout lineupLayout = createKartsLineupVerticalLayout(i);
				add(lineupLayout);
			}
			
			// Create boxes
			boxLimit = boxInput.getValue();
			
			for (int i = 0; i < boxLimit; i++) {
				leftBox.add(new Kart());
				rightBox.add(new Kart());
			}
					       	        
			leftBoxLayout = createBoxesVerticalLayout(leftBox, boxLimit);
			rightBoxLayout = createBoxesVerticalLayout(rightBox, boxLimit);
				
			add(leftBoxLayout);
			add(rightBoxLayout);
		});
	}
		
	private VerticalLayout createBoxesVerticalLayout(LinkedList<Kart> box, double boxLimit) {
		VerticalLayout layout = new VerticalLayout();
		layout.setAlignItems(Alignment.CENTER);
		layout.addClassName("boxes");
		
		for(Kart boxKart : box) {
			Button button = createBoxButtons(boxKart);
			layout.add(button);
		}
		
		return layout;
	}
	
	private VerticalLayout createKartsLineupVerticalLayout(int layoutIndex) {
		VerticalLayout layout = new VerticalLayout();	
		layout.setAlignItems(Alignment.CENTER);
		
		for (int i = layoutIndex * KARTS_PER_COLUMN; i < Math.min((layoutIndex + 1) * KARTS_PER_COLUMN, kartsLineup.size()); i++) {
			Button button = createKartsButtons(kartsLineup.get(i));
			
			layout.add(button);
		}

		return layout;
	}

	private Button createKartsButtons(Kart kart) {
		Button button = new Button("Kart " + kart.getNumber());
		button.addClassName(kart.getSpeedType().toString());

		ContextMenu contextMenu = new ContextMenu();
		contextMenu.setOpenOnClick(true);
		contextMenu.setTarget(button);

		contextMenu.addItem("Sin información", selection -> {
			kart.setSpeedType(SpeedType.NO_INFO);
			button.setClassName(SpeedType.NO_INFO.toString());
		});
		contextMenu.addItem("Rápido", selection -> {
			kart.setSpeedType(SpeedType.FAST);
			button.setClassName(SpeedType.FAST.toString());
		});
		contextMenu.addItem("Lento", selection -> {
			kart.setSpeedType(SpeedType.SLOW);
			button.setClassName(SpeedType.SLOW.toString());
		});
		contextMenu.addItem("Box Izquierda", selection -> {
			Kart kartOut = leftBox.poll();
			leftBox.addLast(kart);
					
			kartsLineup.set(kartsLineup.indexOf(kart), kartOut);
			button.setClassName(kartOut.getSpeedType().toString());
			
			VerticalLayout newLayout = createBoxesVerticalLayout(leftBox, boxLimit);
			replace(leftBoxLayout, newLayout);
			leftBoxLayout = newLayout;
		});
		contextMenu.addItem("Box Derecha", selection -> {
			Kart kartOut = rightBox.poll();
			rightBox.addLast(kart);
			
			kartsLineup.set(kartsLineup.indexOf(kart), kartOut);
			button.setClassName(kartOut.getSpeedType().toString());
			
			VerticalLayout newLayout = createBoxesVerticalLayout(rightBox, boxLimit);
			replace(rightBoxLayout, newLayout);
			rightBoxLayout = newLayout;
		});
		
		return button;
	}
	
	private Button createBoxButtons(Kart kart) {
		Button button = new Button("Kart");
		button.addClassName(kart.getSpeedType().toString());

		ContextMenu contextMenu = new ContextMenu();
		contextMenu.setOpenOnClick(true);
		contextMenu.setTarget(button);

		contextMenu.addItem("Sin información", selection -> {
			kart.setSpeedType(SpeedType.NO_INFO);
			button.setClassName(SpeedType.NO_INFO.toString());
		});
		contextMenu.addItem("Rápido", selection -> {
			kart.setSpeedType(SpeedType.FAST);
			button.setClassName(SpeedType.FAST.toString());
		});
		contextMenu.addItem("Lento", selection -> {
			kart.setSpeedType(SpeedType.SLOW);
			button.setClassName(SpeedType.SLOW.toString());
		});
		
		return button;
	}
}