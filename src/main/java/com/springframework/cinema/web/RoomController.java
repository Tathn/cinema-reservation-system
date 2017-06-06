package com.springframework.cinema.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springframework.cinema.domain.room.Room;
import com.springframework.cinema.domain.room.RoomRepository;
import com.springframework.cinema.domain.room.RoomService;

@Controller
public class RoomController {
	
	private static final String VIEWS_ROOM_CREATE_OR_UPDATE_FORM = "room/createOrUpdateRoomForm";
	
	private final RoomService roomService;
	
	@Autowired
	public RoomController(RoomRepository roomRepository) {
		roomService = new RoomService(roomRepository);
	}
	
	@GetMapping("/admin/rooms")
	public String getManageRoomsView(Model model) {
		Collection<Room> rooms = roomService.findAll();
		model.addAttribute("rooms", rooms);
		return "room/manageRooms";
	}
	
	@GetMapping("/admin/rooms/create")
    public String initCreateRoomForm(@ModelAttribute Room room) {
    	return VIEWS_ROOM_CREATE_OR_UPDATE_FORM;
    }
    
    @PostMapping("/admin/rooms/create")
    public String processCreateRoomForm(@ModelAttribute Room room, BindingResult result, RedirectAttributes redirect) {
    	if(result.hasErrors()) {
    		return VIEWS_ROOM_CREATE_OR_UPDATE_FORM;
    	} else {
    		roomService.save(room);
            redirect.addFlashAttribute("globalMessage", "Successfully created new room!");
            return "redirect:/admin/rooms";
    	}
    }
}
