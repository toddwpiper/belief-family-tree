package au.gov.ipaustralia.extract.config;


import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import au.gov.ipaustralia.extract.web.util.FreemarkerUtil;
import freemarker.cache.TemplateLoader;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

@Configuration
public class ViewConfig {
    private static final Logger LOG = LoggerFactory.getLogger(ViewConfig.class);


    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() throws IOException, TemplateException {
        FreeMarkerConfigurer x = new FreeMarkerConfigurer() {
            @Override public void afterPropertiesSet() throws IOException, TemplateException {
                super.afterPropertiesSet();
                freemarker.template.Configuration c = this.getConfiguration();
                c.setIncompatibleImprovements(freemarker.template.Configuration.VERSION_2_3_23);
                c.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
                c.setAPIBuiltinEnabled(true); // sometimes you want direct access to underlying java objects
                c.setNumberFormat("0.######"); //no commas in numbers
                
                c.setSharedVariable("G_FU", new FreemarkerUtil());
                
                c.setTemplateLoader(new EscapeWrappingTemplateLoader(c.getTemplateLoader()));
            }
        };
        x.setTemplateLoaderPaths("/WEB-INF/view/", "classpath:templates");


        return x;
    }

    @Bean
    public FreeMarkerViewResolver getInternalResourceViewResolver() {
        FreeMarkerViewResolver x = new FreeMarkerViewResolver();
        x.setPrefix("/WEB-INF/view/");
        x.setPrefix("");
        x.setSuffix(".ftl");
        x.setViewClass(FreeMarkerView.class);
        x.setRequestContextAttribute("rc");
        x.setExposeSpringMacroHelpers(true);
        return x;
    }

    /**
     * We don't want to manually specify escaping in every file/macro
     * <p>
     * Here we wrap all templates in whitespacestripping/escaping
     * <p>
     * NOTE: templates which internally use <#noescape>xxx</#noescape> _must_ have an escape directive
     * so things will break if this is removed.
     * <p>
     * NOTE: this is just string concatenation, so it doesn't know about the context it's found in. Specifically:
     * - it will break if a <#ftl> directive is explicitly given in the file
     * - you will get double escaping if you use an <#escape> directive explicitly
     * <p>
     * However this is still a better default than no escaping.
     */
    private static class EscapeWrappingTemplateLoader implements TemplateLoader {
        static final String ESCAPE_PREFIX = "<#ftl strip_whitespace=true><#escape x as x?html>";
        static final String ESCAPE_SUFFIX = "</#escape>";
        private final TemplateLoader delegate;

        EscapeWrappingTemplateLoader(TemplateLoader delegate) {this.delegate = delegate;}

        @Override public Object findTemplateSource(String name) throws IOException {
            return delegate.findTemplateSource(name);
        }

        @Override public long getLastModified(Object templateSource) {
            return delegate.getLastModified(templateSource);
        }

        @Override public Reader getReader(Object templateSource, String encoding) throws IOException {
            Reader reader = delegate.getReader(templateSource, encoding);
            try {
                //TODO is there any way to do this without writing to string first?
                String templateText = IOUtils.toString(reader);
                return new StringReader(ESCAPE_PREFIX + templateText + ESCAPE_SUFFIX);
            } catch (IOException e) {
                LOG.error("Error wrapping template reader", e);
                return reader;
            } finally {
                IOUtils.closeQuietly(reader);
            }
        }

        @Override public void closeTemplateSource(Object templateSource) throws IOException {
            delegate.closeTemplateSource(templateSource);
        }
    }
}
