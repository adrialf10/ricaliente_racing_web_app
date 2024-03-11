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
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@PageTitle("Ricaliente Racing") 
@Route("Race") 
public class RaceView extends HorizontalLayout implements HasUrlParameter<String>{ 

	private final int KARTS_PER_COLUMN = 5;

	private List<Kart> kartsLineup;
	
	private LinkedList<Kart> leftBox;
	private LinkedList<Kart> rightBox;

	private VerticalLayout leftBoxLayout;
	private VerticalLayout rightBoxLayout;	

	private Double boxLimit;
	
	public RaceView() {
		this.kartsLineup = new ArrayList<Kart>();
		
		this.leftBox = new LinkedList<Kart>();
		this.rightBox = new LinkedList<Kart>();

		this.leftBoxLayout = null;
		this.rightBoxLayout = null;	

		this.boxLimit = 0.0;
    }
	
	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		String[] params = parameter.split(";");

		// Create karts lineup
		String[] kartNumbers = params[0].split(",");
		for (String number : kartNumbers) {
			kartsLineup.add(new Kart(number));
		}

		// Create boxes
		boxLimit = Double.parseDouble(params[1]);		
		for (int i = 0; i < boxLimit; i++) {
			leftBox.add(new Kart());
			rightBox.add(new Kart());
		}

		executeRaceView();
	}
		
	public void executeRaceView() {
		int lineuplayoutCount = (int) Math.ceil((double) kartsLineup.size() / KARTS_PER_COLUMN);
		for (int i = 0; i < lineuplayoutCount; i++) {
			VerticalLayout lineupLayout = createKartsLineupVerticalLayout(i);
			add(lineupLayout);
		}

		leftBoxLayout = createBoxesVerticalLayout(leftBox, boxLimit);
		rightBoxLayout = createBoxesVerticalLayout(rightBox, boxLimit);

		add(leftBoxLayout);
		add(rightBoxLayout);
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
			Button button = createKartsButtons(i);

			layout.add(button);
		}

		return layout;
	}

	private Button createKartsButtons(int kartIndex) {
		Kart initialKart = kartsLineup.get(kartIndex);
		
		Button button = new Button("Kart " + initialKart.getNumber());
		button.addClassName(initialKart.getSpeedType().toString());

		ContextMenu contextMenu = new ContextMenu();
		contextMenu.setOpenOnClick(true);
		contextMenu.setTarget(button);

		contextMenu.addItem("Sin información", selection -> {
			kartsLineup.get(kartIndex).setSpeedType(SpeedType.NO_INFO);
			button.setClassName(SpeedType.NO_INFO.toString());
		});
		contextMenu.addItem("Rápido", selection -> {
			kartsLineup.get(kartIndex).setSpeedType(SpeedType.FAST);
			button.setClassName(SpeedType.FAST.toString());
		});
		contextMenu.addItem("Lento", selection -> {
			kartsLineup.get(kartIndex).setSpeedType(SpeedType.SLOW);
			button.setClassName(SpeedType.SLOW.toString());
		});
		contextMenu.addItem("Box Izquierda", selection -> {
			Kart kartIn = kartsLineup.get(kartIndex);
			Kart kartOut = leftBox.poll();
						
			kartOut.setNumber(kartIn.getNumber());
			
			kartsLineup.set(kartIndex, kartOut);
			
			kartIn.setNumber("");
			leftBox.addLast(kartIn);
			
			button.setClassName(kartOut.getSpeedType().toString());

			VerticalLayout newLayout = createBoxesVerticalLayout(leftBox, boxLimit);
			replace(leftBoxLayout, newLayout);
			leftBoxLayout = newLayout;
		});
		contextMenu.addItem("Box Derecha", selection -> {
			Kart kartIn = kartsLineup.get(kartIndex);
			Kart kartOut = rightBox.poll();
			
			kartOut.setNumber(kartIn.getNumber());
			
			kartsLineup.set(kartIndex, kartOut);
			
			kartIn.setNumber("");
			rightBox.addLast(kartIn);
			
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