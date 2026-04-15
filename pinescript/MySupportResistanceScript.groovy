//@version=5
indicator(title="Perfect Square S/R (Previous Day Close) - Full Lines", shorttitle="PS S/R (PDC) - Full Lines", overlay=true)

// Get previous day's closing price
previousDayClose = request.security(syminfo.tickerid, "D", close[1])

// Number of lines above and below
numLines = 10

// Calculate minimum and maximum perfect squares to consider
minSquare = int(math.floor(math.sqrt(previousDayClose - numLines * numLines)))
maxSquare = int(math.ceil(math.sqrt(previousDayClose + numLines * numLines)))

// Plot support/resistance lines
for i = minSquare to maxSquare
    square = i * i
    if square >= previousDayClose - numLines * numLines and square <= previousDayClose + numLines * numLines
        lineColor = square < previousDayClose ? color.green : color.red // Green for below, Red for above

        // Check if a line already exists at this level
        var line existingLine = na
        if not na(existingLine) and line.get_price(existingLine, 0) == square
            line.set_extend(existingLine, extend.both) // Extend existing line to both sides
        else
            existingLine := line.new(bar_index[1], square, bar_index + 100, square, color=lineColor, width=1, style=line.style_dashed, extend=extend.both) // Draw new line and extend it

        // No more labels
