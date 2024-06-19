import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import org.codehaus.groovy.control.messages.Message;
import org.jetbrains.annotations.NotNull;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SearchAction extends AnAction {

    private static final String Google_URL = "https://www.google.com/search?q=";
    private static final String Bing_URL = "https://www.bing.com/search?q=";
    private static final String StackOverflow_URL = "https://stackoverflow.com/search?q=";
    private static final String[] searchEngines = {"Google", "Bing", "StackOverflow"};
    @Override
    public void actionPerformed(@NotNull AnActionEvent actionEvent) {
        Editor editor = actionEvent.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        String selectedText = editor.getSelectionModel().getSelectedText();
        if (selectedText == null) {
            Messages.showErrorDialog("No text selected", "Error");
            return;
        }
        String encodedText = URLEncoder.encode(selectedText, StandardCharsets.UTF_8);

        int chosenEngine = Messages.showChooseDialog("Choose a search engine",
                "Search",
                searchEngines,
                "Google",
                null);

        String url;
        switch (chosenEngine) {
            case 0:
                url = Google_URL + encodedText;
                break;
            case 1:
                url = Bing_URL + encodedText;
                break;
            case 2:
                url = StackOverflow_URL + encodedText;
                break;
            default:
                return;
        }

        BrowserUtil.browse(url);
    }
}