package com.springframework.cinema.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springframework.cinema.domain.room.Room;
import com.springframework.cinema.domain.room.RoomRepository;
import com.springframework.cinema.domain.room.RoomSeat;
import com.springframework.cinema.domain.room.RoomSeatRepository;
import com.springframework.cinema.domain.room.RoomSeatService;
import com.springframework.cinema.domain.room.RoomService;
import com.springframework.cinema.web.beans.validators.del.RoomValidator;

@Controller
public class RoomController {
	
	private static final String VIEWS_ROOM_CREATE_OR_UPDATE_FORM = "room/createOrUpdateRoomForm";
	
	private final RoomService roomService;
	private final RoomValidator roomValidator;
	private final RoomSeatService roomSeatService;
	
	@Autowired
	public RoomController(RoomRepository roomRepository, RoomSeatRepository roomSeatRepository) {
		roomService = new RoomService(roomRepository);
		roomValidator = new RoomValidator(roomRepository);
		roomSeatService = new RoomSeatService(roomSeatRepository);
	}
	
	@InitBinder
	public void initRoomBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(roomValidator);
	}
	
	@GetMapping("/admin/rooms")
	public String getManageRoomsView(Model model) {
		Collection<Room> rooms = roomService.findAll();
		model.addAttribute("rooms", rooms);
		return "room/manageRooms";
	}
	
	@GetMapping("/admin/rooms/show/{roomId}")
	public String getShowRoomView(@PathVariable Long roomId, Model model) {
		Room room = roomService.findById(roomId);
		ArrayList<RoomSeat> roomSeats = (ArrayList<RoomSeat>) roomSeatService.findRoomSeatsByRoomId(roomId);
		//roomSeats.sort(Comparator.comparing(RoomSeat::getLabel));
		roomSeats.sort(new Comparator<RoomSeat>() {
			@Override
			public int compare(RoomSeat o1, RoomSeat o2) {
				Integer o1Label = Integer.parseInt(o1.getLabel());
				Integer o2Label = Integer.parseInt(o2.getLabel());
				return o1Label.compareTo(o2Label);
			}});
		model.addAttribute("seats", roomSeats);
		model.addAttribute("room", room);
		return "room/showRoom";
	}
	
	@GetMapping("/admin/rooms/create")
    public String initCreateRoomForm(@ModelAttribute Room room) {
    	return VIEWS_ROOM_CREATE_OR_UPDATE_FORM;
    }
    
    @PostMapping("/admin/rooms/create")
    public String processCreateRoomForm(@Valid Room room, BindingResult result, RedirectAttributes redirect) {
    	if(result.hasErrors()) {
    		return VIEWS_ROOM_CREATE_OR_UPDATE_FORM;
    	} else {
    		roomService.save(room);
    		
    		Integer roomRows = room.getRows();
    		Integer roomColumns = room.getColumns();
    		Collection<RoomSeat> roomSeats = new ArrayList<RoomSeat>();
    		for(Integer row = 1; row <= roomRows;row++) {
    			for(Integer column = 1; column <= roomColumns;column++) {
    				String rowLabel = row.toString();
    				while(rowLabel.length() < roomRows.toString().length()) {
    					rowLabel = "0" + rowLabel;
    				}
    				String columnLabel = column.toString();
    				while(columnLabel.length() < roomColumns.toString().length()) {
    					columnLabel = "0" + columnLabel;
    				}
    				String label = rowLabel + columnLabel;
    				roomSeats.add(new RoomSeat(label,room));
    			}
    		}
    		roomSeatService.save(roomSeats);
    		
            redirect.addFlashAttribute("globalMessage", "Successfully created new room!");
            return "redirect:/admin/rooms";
    	}
    }
    
    @PostMapping("/admin/rooms/delete/{roomId}")
    public String deleteRoom(@PathVariable Long roomId, RedirectAttributes redir) {
    	roomSeatService.deleteByRoomId(roomId);
    	roomService.delete(roomId);
    	redir.addFlashAttribute("globalMessage","Room removed successfully!");
    	return "redirect:/admin/rooms"; 
    }
}
    
