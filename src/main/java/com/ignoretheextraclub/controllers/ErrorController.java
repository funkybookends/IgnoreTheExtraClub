package com.ignoretheextraclub.controllers;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by caspar on 02/04/17.
 */
@Controller
public class ErrorController extends BasicErrorController implements org.springframework.boot.autoconfigure.web.ErrorController
{
    public static final String ERROR = "error";
    public final static String ERROR_PATH = "/" + ERROR;

    private static final Logger LOG = LoggerFactory.getLogger(ErrorController.class);

    private Meter getErrorPathMeter;
    private Meter renderErrorPageMeter;

    public ErrorController(final ErrorAttributes errorAttributes,
                           final ErrorProperties errorProperties)
    {
        super(errorAttributes, errorProperties);

        LOG.info("{} created with {} & {} and no default ErrorViewResolvers",
                this.getClass().getSimpleName(),
                errorAttributes,
                errorProperties);
    }

    @Autowired
    public ErrorController(final ErrorAttributes errorAttributes,
                           final ErrorProperties errorProperties,
                           final List<ErrorViewResolver> errorViewResolvers)
    {
        super(errorAttributes,
                errorProperties,
                errorViewResolvers);

        LOG.info("{} created with {} & {} with resolvers: {}",
                this.getClass().getSimpleName(),
                errorAttributes, errorProperties,
                errorViewResolvers.stream()
                                  .map(evr -> evr.getClass().getSimpleName())
                                  .collect(Collectors.joining(", ")));
    }

    @Autowired
    public void configureMetrics(final MetricRegistry metricRegistry)
    {
        this.getErrorPathMeter = metricRegistry.meter(MetricRegistry.name("error-controller", "get-path"));
        this.renderErrorPageMeter = metricRegistry.meter(MetricRegistry.name("error-controller", "render-error-page"));
    }

    @Override
    public String getErrorPath()
    {
        this.getErrorPathMeter.mark();
        return ERROR_PATH;
    }

    @Override
    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(final HttpServletRequest request,
                                  final HttpServletResponse response)
    {
        renderErrorPageMeter.mark();
        LOG.info("errorHtml: Request: {}, Response: {}", request, response);
        return super.errorHtml(request, response);
    }

    @Override
    @RequestMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(final HttpServletRequest request)
    {
        renderErrorPageMeter.mark();
        LOG.info("error: Request: {}", request);
        return super.error(request);
    }
}
