package com.epam.timetracking.tag;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class FormatDateTimeTag extends TagSupport {

    private static final long serialVersionUID = -5363984010279023374L;
    private LocalDateTime dateTime;
    private static final Logger log = LogManager.getLogger(FormatDateTimeTag.class);

    @Override
    public int doStartTag() {
        if (dateTime == null) {
            return SKIP_BODY;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = dateTime.format(formatter);
        try {
            pageContext.getOut().print(formatDateTime);
        } catch (IOException e) {
            log.error("Cannot write format of datetime: " + e.getMessage(), e);
        }
        return SKIP_BODY;
    }
}
