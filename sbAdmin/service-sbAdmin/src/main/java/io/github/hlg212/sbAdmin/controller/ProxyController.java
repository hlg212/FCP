package io.github.hlg212.sbAdmin.controller;

import io.github.hlg212.fcf.annotation.RestBody;
import io.github.hlg212.sbAdmin.api.client.SbAdminIndexApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/proxy"})
@Slf4j
public class ProxyController {

    @Autowired
    private SbAdminIndexApi sbAdminIndexApi;

    @RequestMapping(value = "/index",method=RequestMethod.GET,produces = "text/html; charset=UTF-8")
    @RestBody(value = false)
    public String index() {

        String html = sbAdminIndexApi.index();

        int index = html.indexOf("</body>");

        String htmlb = html.substring(0,index) + " <script lang=javascript>document.getElementsByClassName(\"navbar-item\")[4].remove()</script>";


        String css = "<style type=\"text/css\"> .is-narrow a{pointer-events: none;} .is-muted a{pointer-events: none;}</style>";
        String htmle = html.substring(index);


        return htmlb + css + htmle;
    }
}
