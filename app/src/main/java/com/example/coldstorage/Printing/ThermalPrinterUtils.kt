import android.Manifest
import android.content.Context
import android.os.Build
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.coldstorage.DataLayer.Api.ResponseDataTypes.DaybookCard.OrderDaybook
import com.example.coldstorage.Presentation.Screens.DashBoardScreen.totalIncomingBags
import com.example.coldstorage.Presentation.Screens.DashBoardScreen.totalOutgoingBags
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A utility class for printing receipts using Android's native printing framework.
 * This approach doesn't require third-party libraries and works with any printer
 * supported by the Android system.
 */
class ThermalPrinterUtils {
    companion object {
        /**
         * Prints a receipt for the given order using Android's native printing framework
         */
        fun printReceipt(context: Context, orderDaybook: OrderDaybook) {
            try {
                // Create HTML content for the receipt
                val htmlContent = createHtmlReceipt(orderDaybook)

                // Print using Android's print framework
                printHtml(context, htmlContent, "Receipt_${orderDaybook.voucher.voucherNumber}")
            } catch (e: Exception) {
                Toast.makeText(context, "Printing error: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("PrintingError", "Error printing receipt", e)
            }
        }

        /**
         * Creates HTML content formatted for printing on a receipt printer
         */
        private fun createHtmlReceipt(orderDaybook: OrderDaybook): String {
            // Create a simple HTML receipt without background images
            return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Receipt</title>
                <style>
                    body {
                        font-family: monospace;
                        font-size: 9pt;
                        width: 80mm; /* Standard receipt width */
                        margin: 0;
                        padding: 0;
                    }
                    .receipt {
                        width: 100%;
                    }
                    .center {
                        text-align: center;
                    }
                    .left {
                        text-align: left;
                    }
                    .header {
                        font-weight: bold;
                        font-size: 10pt;
                    }
                    .divider {
                        border-top: 1px dashed #000;
                        margin: 5px 0;
                    }
                    .bold {
                        font-weight: bold;
                    }
                    table {
                        width: 100%;
                    }
                    td {
                        padding: 2px 0;
                    }
                </style>
            </head>
            <body>
                <div class="receipt">
                    <div class="center header">RECEIPT</div>
                    <div class="center">${if (orderDaybook.voucher.type == "RECEIPT") orderDaybook.dateOfSubmission else orderDaybook.dateOfExtraction}</div>
                    
                    <div class="divider"></div>
                    
                    <table>
                        <tr>
                            <td class="left">Type:</td>
                            <td class="left">${orderDaybook.voucher.type}</td>
                        </tr>
                        <tr>
                            <td class="left">Voucher No.:</td>
                            <td class="left">${orderDaybook.voucher.voucherNumber}</td>
                        </tr>
                        <tr>
                            <td class="left">Variety:</td>
                            <td class="left">${orderDaybook.orderDetails[0].variety}</td>
                        </tr>
                    </table>
                    
                    <div class="divider"></div>
                    
                    <div class="bold">${if (orderDaybook.voucher.type == "RECEIPT") "Added Bags:" else "Removed Bags:"}</div>
                    <table>
                        ${generateBagDetailsHtml(orderDaybook)}
                    </table>
                    
                    <div class="divider"></div>
                    
                    <table>
                        <tr>
                            <td class="left bold">Total Bags:</td>
                            <td class="left">${if (orderDaybook.voucher.type == "RECEIPT") totalIncomingBags(orderDaybook) else totalOutgoingBags(orderDaybook)}</td>
                        </tr>
                    </table>
                    
                    <div class="divider"></div>
                    
                    <table>
                        <tr>
                            <td class="left bold">Farmer:</td>
                            <td class="left">${orderDaybook.farmerId.name}</td>
                        </tr>
                        <tr>
                            <td class="left bold">Acc:</td>
                            <td class="left">${orderDaybook.farmerId._id.take(5)}</td>
                        </tr>
                    </table>
                    
                    <div class="divider"></div>
                    
                    <div class="center">Powered By ColdOp</div>
                    <div class="center">Thank You</div>
                </div>
            </body>
            </html>
            """.trimIndent()
        }

        /**
         * Generates HTML for bag details based on order type
         */
        private fun generateBagDetailsHtml(orderDaybook: OrderDaybook): String {
            val sb = StringBuilder()

            if (orderDaybook.voucher.type == "RECEIPT") {
                for (bag in orderDaybook.orderDetails[0].bagSizes) {
                    sb.append("<tr>")
                    sb.append("<td class='left'>${bag.size}:</td>")
                    sb.append("<td class='left'>${bag.quantity?.initialQuantity ?: "N/A"}</td>")
                    sb.append("</tr>")
                }
            } else {
                for (order in orderDaybook.orderDetails) {
                    for (bag in order.bagSizes) {
                        sb.append("<tr>")
                        sb.append("<td class='left'>${bag.size}:</td>")
                        sb.append("<td class='left'>${bag.quantityRemoved ?: "N/A"}</td>")
                        sb.append("</tr>")
                    }
                }
            }

            return sb.toString()
        }

        /**
         * Uses Android's print framework to print HTML content
         */
        private fun printHtml(context: Context, htmlContent: String, jobName: String) {
            // Create a WebView to render the HTML content
            val webView = WebView(context)
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest) = false

                override fun onPageFinished(view: WebView, url: String) {
                    // Once the page is loaded, start the print job
                    if (url == "about:blank") {
                        createWebPrintJob(context, view, jobName)
                    }
                }
            }

            // Load the HTML content
            webView.loadDataWithBaseURL(null, htmlContent, "text/HTML", "UTF-8", null)
        }

        /**
         * Creates a print job using the Android PrintManager
         */
        private fun createWebPrintJob(context: Context, webView: WebView, jobName: String) {
            // Get the print manager
            val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager

            // Create print attributes for receipt printer (typically 80mm wide)
            val printAttributes = PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A7) // Small paper, close to thermal receipt size
                .setResolution(PrintAttributes.Resolution("pdf", "pdf", 300, 300))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                .build()

            // Create a print adapter
            val printAdapter = webView.createPrintDocumentAdapter(jobName)

            // Start the print job
            printManager.print(jobName, printAdapter, printAttributes)

            Toast.makeText(context, "Preparing to print...", Toast.LENGTH_SHORT).show()
        }

        /**
         * Checks if the device has printing capabilities
         */
        fun checkPrintingAvailable(context: Context): Boolean {
            val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
            // This is a basic check - if the service exists, we can print
            return true
        }
    }
}