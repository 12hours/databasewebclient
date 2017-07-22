package client.controllers;

import client.domain.Child;
import client.service.ChildrenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChildController {

    @Autowired
    ChildrenService childrenService;

    @RequestMapping(value = {"/savechild"}, method = RequestMethod.POST)
    public String saveChild(@RequestBody String jsonString){
        String result;
        try {
            Child child = childrenService.createChildFromJson(jsonString);
            childrenService.saveChild(child);
            result =  "{ result: success }";
        } catch (Exception e){
            e.printStackTrace();
            result =  "{ result: failure }";
        }
        return result;
    }

    @RequestMapping("/getchild")
    public String getChild(@RequestParam("id") long id) {
        Child child = childrenService.getChildById(id);
        return childrenService.createJsonFromChild(child);
    }

}
