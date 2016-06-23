package com.better517na.JavaServiceRouteHelper.business.JavaTypeAdapter;

import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * cxf listlist泛型的adapter.
 * 
 * @author chunfeng
 */
public class ListListAdapter extends XmlAdapter<ListListConvertor, List<List<Object>>> {
    /**
     * {@inheritDoc}.
     */
    @Override
    public ListListConvertor marshal(List<List<Object>> value) throws Exception {
        ListListConvertor convertor = new ListListConvertor();
        for (List<Object> tvalue : value) {
            convertor.addValue(tvalue);
        }
        return convertor;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<List<Object>> unmarshal(ListListConvertor value) throws Exception {
        return value.getValue();
    }

}
