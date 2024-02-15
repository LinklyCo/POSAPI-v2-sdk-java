package com.linkly.pos.sdk.models.display;

import java.util.ArrayList;
import java.util.List;

import com.linkly.pos.sdk.models.PosApiResponse;
import com.linkly.pos.sdk.models.enums.GraphicCode;
import com.linkly.pos.sdk.models.enums.InputType;
import com.linkly.pos.sdk.service.IPosApiEventListener;

/**
 * This message is returned asynchronously when a display event occurs on the PIN pad. To handle
 * display events refer to
 * {@link IPosApiEventListener#display(java.util.UUID, com.linkly.pos.sdk.models.PosApiRequest,
 * DisplayResponse)}
 */
public class DisplayResponse extends PosApiResponse {

    private int numberOfLines;
    private int lineLength;
    private List<String> displayText = new ArrayList<>();
    private boolean cancelKeyFlag;
    private boolean acceptYesKeyFlag;
    private boolean declineNoKeyFlag;
    private boolean authoriseKeyFlag;
    private boolean okKeyFlag;
    private InputType inputType;
    private GraphicCode graphicCode;

    /**
     * Number of lines to display.
     * 
     * @return int
     */
    public int getNumberOfLines() {
        return numberOfLines;
    }

    /**
     * Sets the numberOfLines.
     * 
     * @param numberOfLines
     *            The int value of numberOfLines.
     */
    public void setNumberOfLines(int numberOfLines) {
        this.numberOfLines = numberOfLines;
    }

    /**
     * Number of character per display line.
     * 
     * @return int
     */
    public int getLineLength() {
        return lineLength;
    }

    /**
     * Sets the lineLength.
     * 
     * @param lineLength
     *            The int value of lineLength.
     */
    public void setLineLength(int lineLength) {
        this.lineLength = lineLength;
    }

    /**
     * Text to be displayed. Each display line is concatenated.
     * example: [&quot; SWIPE CARD &quot;,&quot; &quot;]
     * 
     * @return {@link List} of {@link String}
     */
    public List<String> getDisplayText() {
        return displayText;
    }

    /**
     * Sets the displayText.
     * 
     * @param displayText
     *            {@link List} of {@link String}.
     */
    public void setDisplayText(List<String> displayText) {
        this.displayText = displayText;
    }

    /**
     * Indicates whether the Cancel button is to be displayed.
     * 
     * @return boolean
     */
    public boolean isCancelKeyFlag() {
        return cancelKeyFlag;
    }

    /**
     * Sets the cancelKeyFlag.
     * 
     * @param cancelKeyFlag
     *            The boolean value of cancelKeyFlag.
     */
    public void setCancelKeyFlag(boolean cancelKeyFlag) {
        this.cancelKeyFlag = cancelKeyFlag;
    }

    /**
     * Indicates whether the Accept/Yes button is to be displayed.
     * 
     * @return boolean
     */
    public boolean isAcceptYesKeyFlag() {
        return acceptYesKeyFlag;
    }

    /**
     * Sets the acceptYesKeyFlag.
     * 
     * @param acceptYesKeyFlag
     *            The boolean value of acceptYesKeyFlag.
     */
    public void setAcceptYesKeyFlag(boolean acceptYesKeyFlag) {
        this.acceptYesKeyFlag = acceptYesKeyFlag;
    }

    /**
     * Indicates whether the Decline/No button is to be displayed.
     * 
     * @return boolean
     */
    public boolean isDeclineNoKeyFlag() {
        return declineNoKeyFlag;
    }

    /**
     * Sets the value of declineNoKeyFlag.
     * 
     * @param declineNoKeyFlag
     *            The boolean value of declineNoKeyFlag.
     */
    public void setDeclineNoKeyFlag(boolean declineNoKeyFlag) {
        this.declineNoKeyFlag = declineNoKeyFlag;
    }

    /**
     * Indicates whether the Authorise button is to be displayed.
     * 
     * @return boolean
     */
    public boolean isAuthoriseKeyFlag() {
        return authoriseKeyFlag;
    }

    /**
     * Sets the authoriseKeyFlag.
     * 
     * @param authoriseKeyFlag
     *            The boolean value of authoriseKeyFlag.
     */
    public void setAuthoriseKeyFlag(boolean authoriseKeyFlag) {
        this.authoriseKeyFlag = authoriseKeyFlag;
    }

    /**
     * Indicates whether the OK button is to be displayed.
     * 
     * @return boolean
     */
    public boolean isOkKeyFlag() {
        return okKeyFlag;
    }

    /**
     * Sets the okKeyFlag.
     * 
     * @param okKeyFlag
     *            The boolean value of okKeyFlag.
     */
    public void setOkKeyFlag(boolean okKeyFlag) {
        this.okKeyFlag = okKeyFlag;
    }

    /**
     * Indicates the input type requested by the pinpad.
     * 
     * @return InputType
     */
    public InputType getInputType() {
        return inputType;
    }

    /**
     * Sets the inputType.
     * 
     * @param inputType
     *            The input type value of inputType.
     */
    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }

    /**
     * Indicates the graphic code type to be displayed.
     * 
     * @return GraphicCode
     */
    public GraphicCode getGraphicCode() {
        return graphicCode;
    }

    /**
     * Sets the graphicCode.
     * 
     * @param graphicCode
     *            The graphic code type value of graphicCode.
     */
    public void setGraphicCode(GraphicCode graphicCode) {
        this.graphicCode = graphicCode;
    }
}
