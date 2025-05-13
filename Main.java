package TicketBooking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Bus {
    String number;
    String route;
    int totalSeats;
    int bookedSeats;

    public Bus(String number, String route, int totalSeats) {
        this.number = number;
        this.route = route;
        this.totalSeats = totalSeats;
        this.bookedSeats = 0;
    }

    public int availableSeat() {
        return totalSeats - bookedSeats;
    }

    public boolean bookSeat(int quantity) {
        if (availableSeat() >= quantity) {
            bookedSeats += quantity;
            return true;
        } else {
            return false;
        }
    }
}

class Passenger {
    String name;
    String phone;
    Bus bus;
    int quantity;

    public Passenger(String name, String phone, Bus bus, int quantity) {
        this.name = name;
        this.phone = phone;
        this.bus = bus;
        this.quantity = quantity;
    }
}

class Admin {
    private String username = "Admin";
    private String password = "1234";

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}

class BusSystem {
    ArrayList<Bus> busList = new ArrayList<>();
    ArrayList<Passenger> passengerList = new ArrayList<>();
    boolean adminLoggedIn = false;

    public void addBus(String number, String route, int seats) {
        Bus bus = new Bus(number, route, seats);
        busList.add(bus);
    }

    public void bookTicket(String busNumber, String phone, String name, int quantity) {
        Bus bus = null;
        for (Bus b : busList) {
            if (b.number.equals(busNumber)) {
                bus = b;
                break;
            }
        }
        if (bus != null) {
            if (bus.bookSeat(quantity)) {
                Passenger passenger = new Passenger(name, phone, bus, quantity);
                passengerList.add(passenger);
                JOptionPane.showMessageDialog(null, quantity + " ticket(s) booked successfully for " + name + "\nTotal Fare: " + (500 * quantity));
            } else {
                JOptionPane.showMessageDialog(null, "Seats not available.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bus not found.");
        }
    }

    public String showBuses() {
        StringBuilder sb = new StringBuilder();
        if (busList.isEmpty()) {
            sb.append("No buses available.\n");
        } else {
            for (Bus bus : busList) {
                sb.append("Bus Number: ").append(bus.number)
                  .append(", Route: ").append(bus.route)
                  .append(", Available Seats: ").append(bus.availableSeat()).append("\n");
            }
        }
        return sb.toString();
    }
}

public class Main {
	static BusSystem system = new BusSystem();
    static Admin admin = new Admin();
    
    //next 
    public static void moveToNextField(JTextField current, JTextField next) {
        current.addActionListener(e -> next.requestFocusInWindow());
    }



	public static void main(String[] args) {
		  SwingUtilities.invokeLater(() -> createMainMenu());


	}
	 public static void createMainMenu() {
	        JFrame frame = new JFrame("Bus Ticket Booking System");
	        frame.setSize(400, 400);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLayout(new GridLayout(4, 1, 10, 10));

	        JButton adminLoginButton = new JButton("Admin Login");
	        JButton bookTicketButton = new JButton("Book Ticket");
	        JButton viewBusesButton = new JButton("View Bus List");
	        JButton exitButton = new JButton("Exit");

	        frame.add(adminLoginButton);
	        frame.add(bookTicketButton);
	        frame.add(viewBusesButton);
	        frame.add(exitButton);

	        adminLoginButton.addActionListener(e -> adminLogin());
	        bookTicketButton.addActionListener(e -> bookTicket());
	        viewBusesButton.addActionListener(e -> viewBuses());
	        exitButton.addActionListener(e -> System.exit(0));

	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	    }

	    public static void adminLogin() {
	        JTextField usernameField = new JTextField();
	        JPasswordField passwordField = new JPasswordField();
	        
	        //next
	        moveToNextField(usernameField, passwordField);
	        Object[] fields = {
	            "Username:", usernameField,
	            "Password:", passwordField
	        };
	        int option = JOptionPane.showConfirmDialog(null, fields, "Admin Login", JOptionPane.OK_CANCEL_OPTION);

	        if (option == JOptionPane.OK_OPTION) {
	            String username = usernameField.getText();
	            String password = new String(passwordField.getPassword());
	            if (admin.login(username, password)) {
	                JOptionPane.showMessageDialog(null, "Login Successful!");
	                createAdminMenu();
	            } else {
	                JOptionPane.showMessageDialog(null, "Incorrect username or password!");
	            }
	        }
	    }

	    public static void createAdminMenu() {
	        JFrame adminFrame = new JFrame("Admin Panel");
	        adminFrame.setSize(400, 300);
	        adminFrame.setLayout(new GridLayout(3, 1, 10, 10));

	        JButton addBusButton = new JButton("Add Bus");
	        JButton viewBusButton = new JButton("View Bus List");
	        JButton logoutButton = new JButton("Logout");

	        adminFrame.add(addBusButton);
	        adminFrame.add(viewBusButton);
	        adminFrame.add(logoutButton);

	        addBusButton.addActionListener(e -> addBus());
	        viewBusButton.addActionListener(e -> viewBuses());
	        logoutButton.addActionListener(e -> adminFrame.dispose());

	        adminFrame.setLocationRelativeTo(null);
	        adminFrame.setVisible(true);
	    }

	    public static void addBus() {
	        JTextField numberField = new JTextField();
	        JTextField routeField = new JTextField();
	        JTextField seatsField = new JTextField();
	        
	        //next
	        moveToNextField(numberField, routeField);
	        moveToNextField(routeField, seatsField);
	        
	        
	        
	        Object[] fields = {
	            "Bus Number:", numberField,
	            "Bus Route:", routeField,
	            "Total Seats:", seatsField
	        };
	        int option = JOptionPane.showConfirmDialog(null, fields, "Add Bus", JOptionPane.OK_CANCEL_OPTION);

	        if (option == JOptionPane.OK_OPTION) {
	            String number = numberField.getText();
	            String route = routeField.getText();
	            int seats;
	            try {
	                seats = Integer.parseInt(seatsField.getText());
	                system.addBus(number, route, seats);
	                JOptionPane.showMessageDialog(null, "Bus Added Successfully!");
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(null, "Invalid number of seats!");
	            }
	        }
	    }

	    public static void bookTicket() {
	        JTextField busNumberField = new JTextField();
	        JTextField phoneField = new JTextField();
	        JTextField nameField = new JTextField();
	        JTextField quantityField = new JTextField();
	        
	        //next
	        
	        moveToNextField(busNumberField, phoneField);
	        moveToNextField(phoneField, nameField);
	        moveToNextField(nameField, quantityField);
	        
	        Object[] fields = {
	            "Bus Number:", busNumberField,
	            "Phone Number:", phoneField,
	            "Name:", nameField,
	            "How Many Seats:", quantityField
	        };
	        int option = JOptionPane.showConfirmDialog(null, fields, "Book Ticket", JOptionPane.OK_CANCEL_OPTION);

	        if (option == JOptionPane.OK_OPTION) {
	            String busNumber = busNumberField.getText();
	            String phone = phoneField.getText();
	            String name = nameField.getText();
	            int quantity;
	            try {
	                quantity = Integer.parseInt(quantityField.getText());
	                system.bookTicket(busNumber, phone, name, quantity);
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(null, "Invalid quantity!");
	            }
	        }
	    }

	    public static void viewBuses() {
	        if (system != null) {
	            String buses = system.showBuses();
	            JOptionPane.showMessageDialog(null, buses, "Available Buses", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(null, "System not initialized", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }

	

}
