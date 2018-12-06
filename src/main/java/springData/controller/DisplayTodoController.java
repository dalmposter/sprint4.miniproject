package springData.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import springData.OrganizerApp;
import springData.domain.Organizer;
import springData.domain.OrganizerUser;
import springData.domain.Todo;
import springData.repository.TodoRepository;
import springData.repository.UserRepository;

@Controller
@RequestMapping("/")
public class DisplayTodoController {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	TodoRepository todoRepo;
	
	@RequestMapping("/")
	public String start() {
		return "redirect:/list";
	}

	@RequestMapping("/list")
	public String listTodos(Model model, Principal principal) {
		
		OrganizerUser user = userRepo.findByLogin(principal.getName());
		List<Todo> todos = new ArrayList<>();
		
		switch(user.getRole().getRole())
		{
			case "MANAGER":
				for(Organizer o : user.getOrganizers())
				{
					todos.addAll(o.getTodos());
				}
				break;
			case "ASSISTANT":
				todoRepo.findAll().forEach( t -> todos.add(t) );
				break;
		}
		
		if(todos.isEmpty()) return "NoTodo";
		
		model.addAttribute("todos", todos);
		return "ListTodos";
		
		/*
		List<Todo> todos = OrganizerApp.organizer.getTodos();
		if (todos.isEmpty()) {
			return "NoTodo";
		} else {
			model.addAttribute("todos", OrganizerApp.organizer.getTodos());
		}
		return "ListTodos";
		*/
	}

	@RequestMapping(value = "/next", method = RequestMethod.GET)
	public String next(Model model, Principal principal) {
		
		OrganizerUser user = userRepo.findByLogin(principal.getName());
		List<Todo> todos = new ArrayList<>();
		
		switch(user.getRole().getRole())
		{
			case "MANAGER":
				for(Organizer o : user.getOrganizers())
				{
					todos.addAll(o.getTodos());
				}
				break;
			case "ASSISTANT":
				todoRepo.findAll().forEach( t -> todos.add(t) );
				break;
		}
		
		if(todos.isEmpty()) return "NoTodo";
		
		Todo highest = todos.get(0);
		for (Todo t : todos) {
			if (t.getPriority() > highest.getPriority()) {
				highest = t;
			}
		}
		
		model.addAttribute("todo", highest);
		
		return "NextTodo";
		
		/*
		Todo t = null;
		try {
			t = OrganizerApp.organizer.getHighestPrioTodo();
		} catch (Exception e) {
			return "NoTodo";
		}
		model.addAttribute("todo", t);
		return "NextTodo";
		*/
	}

}
