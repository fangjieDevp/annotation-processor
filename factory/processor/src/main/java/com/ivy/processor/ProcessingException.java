package com.ivy.processor;

import javax.lang.model.element.Element;

/**
 * Function: com.ivy.processor
 *
 * @author: fangjie07
 * @date: 2018/5/27
 */
public class ProcessingException extends Exception {
    private Element element;

    public ProcessingException(Element element, String msg, Object... args) {
        super(String.format(msg, args));
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
