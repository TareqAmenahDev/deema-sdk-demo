package com.example.testandroidsdk

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deema.v1.DeemaSDKResult
import com.deema.v1.PaymentStatus
import com.example.testandroidsdk.ui.theme.TestAndroidSdkTheme
import me.deema.sdk.DeemaSDK
import me.deema.sdk.DeemaSDKResult
import me.deema.sdk.Environment
import me.deema.sdk.PaymentStatus
import me.deema.sdk.ui.theme.DeemaSDKAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestAndroidSdkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val context = LocalContext.current

    val paymentLauncher = rememberLauncherForActivityResult(DeemaSDKResult()) { status ->
        when (status) {
            is PaymentStatus.Success -> {
                Toast.makeText(context, "Payment successful!", Toast.LENGTH_SHORT).show()
            }
            is PaymentStatus.Failure -> {
                Toast.makeText(context, "Payment failed: ${status.message}", Toast.LENGTH_SHORT).show()
            }
            is PaymentStatus.Canceled -> {
                Toast.makeText(context, "Payment canceled.", Toast.LENGTH_SHORT).show()
            }
            is PaymentStatus.Unknown -> {
                Toast.makeText(context, "Unknown status: ${status.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // UI content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Deema Payment",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(onClick = {
            DeemaSDK.launch(
                environment = Environment.Production,
                currency = "KWD",
                purchaseAmount = "100.00",
                sdkKey = "your-production-sdk-key",
                merchantOrderId = "order123",
                launcher = paymentLauncher
            )
        }) {
            Text(text = "Pay Now")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestAndroidSdkTheme {
        Greeting("Android")
    }
}