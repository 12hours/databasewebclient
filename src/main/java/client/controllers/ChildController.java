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
        Child child = childrenService.createChildFromJson(jsonString);
        try {
            childrenService.saveChild(child);
        } catch (Exception e){

        }
        return "";
    }

    @RequestMapping("/getchild")
    public String getChild(@RequestParam("id") long id) {
        Child child = childrenService.getChildById(id);
        return childrenService.createJsonFromChild(child);
    }

}


