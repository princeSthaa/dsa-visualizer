package zpt.dsaVis;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import zpt.dsaVis.algorithms.Sorting.BubbleSort;
import zpt.dsaVis.algorithms.Sorting.MergeSort;
import zpt.dsaVis.algorithms.Sorting.QuickSort;
import zpt.dsaVis.algorithms.Sorting.SelectionSort;
import zpt.dsaVis.algorithms.Sorting.insertionSort;
import zpt.dsaVis.algorithms.searching.BinarySearch;
import zpt.dsaVis.algorithms.searching.LinearSearch;

@Controller
public class mainController {
    Map<String, List<AlgorithmDetails>> high = new LinkedHashMap<>();
    Map<String, Object> objectList = new LinkedHashMap<>();

    mainController() {
        high.put("Searching", List.of(
            new AlgorithmDetails("LinearSearch","/algo/LinearSearch"),
            new AlgorithmDetails("BinarySearch","/algo/BinarySearch")
        ));
        high.put("Sorting", List.of(
            new AlgorithmDetails("BubbleSort","/algo/BubbleSort"),
            new AlgorithmDetails("QuickSort","/algo/QuickSort"),
            new AlgorithmDetails("InsertionSort","/algo/InsertionSort"),
            new AlgorithmDetails("SelectionSort","/algo/SelectionSort"),
            new AlgorithmDetails("MergeSort","/algo/MergeSort")
        ));
        
        objectList.put("BubbleSort", new BubbleSort());
        objectList.put("InsertionSort", new insertionSort());
        objectList.put("MergeSort", new MergeSort());
        objectList.put("QuickSort", new QuickSort());
        objectList.put("SelectionSort", new SelectionSort());
        objectList.put("LinearSearch", new LinearSearch());
        objectList.put("BinarySearch", new BinarySearch());         
    }

    @GetMapping("/")
    public String homePage(Model m) {
        m.addAttribute("homePageCss","/css/homepage.css"); 
        return "HomePage";
    }

    @GetMapping("/canvas")
    public String canvas(
    @RequestParam(required = false) String type,Model m) 
    {
        Map<String, List<AlgorithmDetails>> filteredAlgorithms;
        
        if (type != null) {
            String normalizedType = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
            filteredAlgorithms = high.containsKey(normalizedType) 
                ? Map.of(normalizedType, high.get(normalizedType))
                : high;
        } else {
            filteredAlgorithms = high;
        }
    
        m.addAttribute("algorithms", filteredAlgorithms);
        m.addAttribute("headerFooterCss","/css/headerFooter.css");
        m.addAttribute("infoDivCss","/css/infoDiv.css");
        return "canvas";
    }

    @GetMapping("/about")
    public String about(Model m) {
        m.addAttribute("aboutCss","/css/about.css");
        return "about";
    }

    @GetMapping("/algo/{projectKey}")
    public String mainFunction(
            @PathVariable String projectKey,
            Model m
    ) {
//        int[] arrayToSort = {1,2,3,4,5,6,7}; // Example array
        int[] arrayToSort = {5,2,8,1,2,3,5}; // Example array
        Algorithm sorter = (Algorithm) objectList.get(projectKey);
        if(sorter instanceof SearchAlgorithm)
            ((SearchAlgorithm) sorter).setTarget(8);
        sorter.implementAlgorithm(arrayToSort);

        m.addAttribute("steps", sorter.getSteps());
        m.addAttribute("title",projectKey);
        return "mainFunction";

    }
}
