import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;

import java.util.Date;


// обработчик запросов
public class IndexController {

    @Autowired
    private UserDAO users;

    public void setUsers(UserDAO users) {
        this.users = users;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String indexPage(ModelMap model){
        IndexPageBean bean = new IndexPageBean(new Date(), users.listUsers());
        model.addAttribute("bean", bean);
        return "index";
    }

}
