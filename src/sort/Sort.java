/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sort;

import entities.Institution;
import entities.Programme;
import java.util.Comparator;
import java.util.Map;

/**
 * =
 *
 * @author wests
 */
public class Sort {

    public Comparator<Map.Entry<String, Institution>> sortName = new Comparator<Map.Entry<String, Institution>>() {
        @Override
        public int compare(Map.Entry<String, Institution> o1, Map.Entry<String, Institution> o2) {
            return o1.getValue().getInstitutionName()
                    .compareTo(o1.getValue().getInstitutionName());
        }
    };

    public Comparator<Programme> sortProgram = (Programme o1, Programme o2) -> o1.getProgName().compareTo(o2.getProgName());

}
