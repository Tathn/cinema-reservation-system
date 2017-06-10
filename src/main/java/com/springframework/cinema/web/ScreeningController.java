package com.springframework.cinema.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springframework.cinema.domain.movie.Movie;
import com.springframework.cinema.domain.movie.MovieRepository;
import com.springframework.cinema.domain.movie.MovieService;
import com.springframework.cinema.domain.room.Room;
import com.springframework.cinema.domain.room.RoomRepository;
import com.springframework.cinema.domain.room.RoomSeat;
import com.springframework.cinema.domain.room.RoomSeatRepository;
import com.springframework.cinema.domain.room.RoomSeatService;
import com.springframework.cinema.domain.room.RoomService;
import com.springframework.cinema.domain.screening.Screening;
import com.springframework.cinema.domain.screening.ScreeningRepository;
import com.springframework.cinema.domain.screening.ScreeningSeat;
import com.springframework.cinema.domain.screening.ScreeningSeatRepository;
import com.springframework.cinema.domain.screening.ScreeningSeatService;
import com.springframework.cinema.domain.screening.ScreeningService;

@Controller
public class ScreeningController {
	
	private final ScreeningService screeningService;
	private final ScreeningSeatService screeningSeatService;
	private final MovieService movieService;
	private final RoomService roomService;
	private final RoomSeatService roomSeatService;
	
	@Autowired
	public ScreeningController(ScreeningRepository screeningRepository, ScreeningSeatRepository screeningSeatRepository, 
			MovieRepository movieRepository, RoomRepository roomRepository, RoomSeatRepository roomSeatRepository) {
		screeningService = new ScreeningService(screeningRepository);
		screeningSeatService = new ScreeningSeatService(screeningSeatRepository);
		movieService = new MovieService(movieRepository);
		roomService = new RoomService(roomRepository);
		roomSeatService = new RoomSeatService(roomSeatRepository);
	}
	
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new org.springframework.format.Formatter<Room>() {

			@Override
			public String print(Room arg0, Locale arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Room parse(String roomId, Locale arg1) throws ParseException {
				Room room = roomService.findById(Long.valueOf(roomId));
				if (room == null)
					System.out.println("ROOM JEST PUSTY WCZESNIEJ. nazwa: " + roomId);
				return room;
			}
		});
        
        binder.addCustomFormatter(new org.springframework.format.Formatter<Movie>() {

			@Override
			public String print(Movie arg0, Locale arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Movie parse(String movieId, Locale arg1) throws ParseException {
				return movieService.findById(Long.valueOf(movieId));
			}
		});
    }
	
	
	@GetMapping("/employee/screenings")
	public String getManageScreeningsView(Model model) {
		Collection<Screening> screenings = screeningService.findAll();
		model.addAttribute("screenings", screenings);
		return "screening/manageScreenings";
	}
	
	@GetMapping("/employee/screenings/show/{screeningId}")
	public String getShowScreeningView(@PathVariable Long screeningId, Model model) {
		Screening screening = screeningService.findById(screeningId);
		ArrayList<ScreeningSeat> screeningSeats = (ArrayList<ScreeningSeat>) screeningSeatService.findScreeningSeatsByScreeningId(screeningId);
		//roomSeats.sort(Comparator.comparing(RoomSeat::getLabel));
		screeningSeats.sort(new Comparator<ScreeningSeat>() {
			@Override
			public int compare(ScreeningSeat o1, ScreeningSeat o2) {
				Integer o1Label = Integer.parseInt(o1.getLabel());
				Integer o2Label = Integer.parseInt(o2.getLabel());
				return o1Label.compareTo(o2Label);
			}});
		model.addAttribute("seats", screeningSeats);
		model.addAttribute("screening", screening);
		return "screening/showScreening";
	}
	
	@GetMapping("/employee/screenings/create")
	public String initScreeningCreateForm(@ModelAttribute Screening screening, Model model) {
		model.addAttribute("movies", movieService.findAll());
		model.addAttribute("rooms", roomService.findAll());
		return "screening/createOrUpdateScreeningForm";
	}
	
	@PostMapping("/employee/screenings/create")
	public String processScreeningCreateForm(@Valid Screening screening, BindingResult result, RedirectAttributes redir) {
		if(result.hasErrors()) {
			return "screening/createOrUpdateScreeningForm";
			//TODO Model gets lost when processing Post request. Fix needed.
			//Temporary fix redirects instead of returning view name
			//return "redirect:/employee/screenings/create";
		} else {
			Room scrRoom = screening.getRoom();
			if(scrRoom == null)
				System.out.println("ROOM JEST PUSTY");
			Collection<RoomSeat> roomSeats = roomSeatService.findRoomSeatsByRoomId(scrRoom.getId());
			screeningService.save(screening);
			screeningSeatService.saveRoomSeatsAsScreeningSeats(roomSeats, screening);
			//TODO I think I messed with ScreeningSeat and RoomSeat services (and ScreeningSeat constructor) too much (need to investigate)
			return "redirect:/employee/screenings";
		}
	}
	
	@PostMapping("/employee/screenings/delete/{screeningId}")
    public String deleteRoom(@PathVariable Long screeningId, RedirectAttributes redir) {
    	screeningSeatService.deleteByScreeningId(screeningId);
    	screeningService.delete(screeningId);
    	redir.addFlashAttribute("globalMessage","Screening removed successfully!");
    	return "redirect:/employee/screenings"; 
    }
}
