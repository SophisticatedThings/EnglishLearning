package artem.strelcov.corporativeapplication.controller;


import artem.strelcov.corporativeapplication.model.AnswerWrapper;
import artem.strelcov.corporativeapplication.model.FeedbackResponse;
import artem.strelcov.corporativeapplication.model.Role;
import artem.strelcov.corporativeapplication.model.User;
import artem.strelcov.corporativeapplication.service.ResponseService;
import artem.strelcov.corporativeapplication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static artem.strelcov.corporativeapplication.model.Role.VISITOR;


@Slf4j
@Controller
@RequestMapping("/api/v1")
public class AppController {
    UserService userService;
    ResponseService responseService;
    private Long localCounter = 0L;

    @Autowired
    public AppController(UserService userService, ResponseService responseService) {
        this.userService = userService;
        this.responseService = responseService;
    }

    @GetMapping("/define")
    public String defineBasePage(Authentication authentication) {
        Role role = userService.findRole(authentication.getName());
        if (role.equals(VISITOR))
            return "redirect:";
        return "redirect:/api/v1/users";
    }

    @GetMapping
    public String homePage() {
        return "homePage";
    }

    @GetMapping("/chat")
    public String getChat() {
        return "chat";
    }

    @GetMapping("/learning")
    public String getLearningPage() {
        return "learning_materials";
    }

    @GetMapping("/tests")
    public String getTests(@RequestParam(value = "title", required = false) String title,
                           Model model) {
        if (title == null) {
            return "tests";
        }
        AnswerWrapper answerWrapper = new AnswerWrapper();
        model.addAttribute("form", answerWrapper);
        return title;
    }

    @GetMapping("/tests/{title}/results")
    public String getTestsResults(@ModelAttribute("form") AnswerWrapper answerWrapper,
                                  @PathVariable("title") String title, Model model) {
        ArrayList<String> results = answerWrapper.getAnswerList();
        model.addAttribute("results", processResults(results, title));
        model.addAttribute("trueAnswers", getTrueAnswers(title));
        model.addAttribute("accuracy", getAccuracy(results, title));
        return "results";
    }

    private double getAccuracy(ArrayList<String> results, String title) {
        double amountOfRightAnswers = 0;
        try {
            String path = "C:/Users/sophi/IdeaProjects/EnglishLearning/src/main/resources/files/" + title + ".txt";
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            Iterator<String> iter = results.iterator();
            int counter = 0;
            while (line != null) {
                if (line.equals(iter.next()))
                    counter++;
                line = br.readLine();
            }
            amountOfRightAnswers = counter;
        } catch (IOException e) {
            System.out.println("File Not Found");
        }
        System.out.println(amountOfRightAnswers * 10);
        return amountOfRightAnswers * 10;
    }

    private List<String> getTrueAnswers(String title) {
        ArrayList<String> trueAnswers = new ArrayList<>();
        try {
            String path = "C:/Users/sophi/IdeaProjects/EnglishLearning/src/main/resources/files/" + title + ".txt";
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            trueAnswers.add(line);
            while (line != null) {
                line = br.readLine();
                trueAnswers.add(line);
            }
        } catch (IOException e) {
            System.out.println("File Not Found");
        }
        return trueAnswers;
    }

    private LinkedHashMap<Integer, Boolean> processResults(ArrayList<String> results, String title) {
        LinkedHashMap<Integer, Boolean> map = new LinkedHashMap<>();
        System.out.println(results);
        try {
            String path = "C:/Users/sophi/IdeaProjects/EnglishLearning/src/main/resources/files/" + title + ".txt";
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            Iterator<String> iter = results.iterator();
            int index = 0;
            while (line != null) {
                if (Objects.equals(iter.next(), line)) {
                    map.put(index, true);
                } else {
                    map.put(index, false);
                }
                index++;
                line = br.readLine();
            }
            System.out.println(map);

        } catch (IOException e) {
            System.out.println("File Not Found");
        }

        return map;
    }

    @GetMapping("/feedback")
    public String getFeedbackPage(Model model) {
        model.addAttribute("responseEntity", new FeedbackResponse());
        return "feedback";
    }

    @GetMapping("/feedback/processing")
    public String processFeedback(@ModelAttribute("responseEntity") FeedbackResponse feedbackResponse,
                                  Authentication authentication) {
        startProcessFeedback(feedbackResponse.getResponse());
        feedbackResponse.setTitle("comment" + localCounter++);
        feedbackResponse.setUserId(userService.findByEmail(authentication.getName()).getId());
        responseService.saveResponse(feedbackResponse);
        return "feedback-success";
    }

    private void startProcessFeedback(String response) {
        String path = "C:/Users/sophi/IdeaProjects/EnglishLearning/src/main/resources/feedback/comment" + localCounter;
        try {
            Files.write(Paths.get(path), response.getBytes());
        } catch (IOException e) {
            System.out.println("Bad");
        }
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<User> users = userService.users();
        model.addAttribute("listUsers", users);
        model.addAttribute("user", new User());
        return "users";
    }

    @GetMapping("/users/block/{email}")
    public String blockUser(
            @ModelAttribute("user") User user, @PathVariable("email") String id) {
        userService.blockUser(user.getEmail());
        return "redirect:/api/v1/users";
    }

    @GetMapping("/users/unblock/{email}")
    public String unblockUser(
            @ModelAttribute("user") User user, @PathVariable("email") String id) {
        userService.unblockUser(user.getEmail());
        return "redirect:/api/v1/users";
    }

    @GetMapping("/users/reviews")
    public String getReviews(Model model) throws IOException {
        model.addAttribute("reviews", responseService.getResponses());
        return "reviews";
    }

    public List<String> findReviews() throws IOException{
        String path = "C:/Users/sophi/IdeaProjects/EnglishLearning/src/main/resources/feedback";
        final File folder = new File(path);
        List<String> reviews = listFilesForFolder(folder);
        return reviews;
    }

    public List<String> listFilesForFolder(final File folder) throws IOException {
        List<String> files = new LinkedList<>();
        String path = "C:/Users/sophi/IdeaProjects/EnglishLearning/src/main/resources/feedback/";
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                byte[] encoded = Files.readAllBytes(Paths.get(path + fileEntry.getName()));
                files.add(new String(encoded));
            }
        }
        return files;
    }
    @GetMapping("/users/find")
    public String findUserById(@ModelAttribute("user") User user, Model model,
    @RequestParam("id") Long id){
        Optional<User> optionalUser = userService.findById(user.getId());
        if(optionalUser.isPresent())
            model.addAttribute("user", optionalUser.get());
        else
            return "no-such-user";
        return "user";
    }
}


