package com.example.bottomnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bottomnavigation.ui.theme.BottomNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /*
            To display this bottom navigation bar we will use the Scaffold.Scaffold have specific containers for predefined
            views like bottom navigation view. so if we pass the bottom navigation view to the scaffold then scaffold make sure
            that it display at the bottom.
             */
            BottomNavigationTheme {
                var navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        bottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Home",
                                    route = "home",
                                    image = Icons.Default.Home,
                                ),
                                BottomNavItem(
                                    name = "Chat",
                                    route = "chat",
                                    image = Icons.Default.Notifications,
                                ),
                                BottomNavItem(
                                    name = "Settings",
                                    route = "settings",
                                    image = Icons.Default.Settings,
                                )
                            ),
                            navController = navController,
                            onItemClick = {
                                // 'it' refer to the current item we clicked on. I don't know why it refer to the current
                                // clicked bottom nav item.
                                navController.navigate(it.route)
                            })
                    }
                ) {
                    navigation(navController = navController)

                }

            }
        }
    }
}

@Composable
fun navigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = "home"){
        composable(route="home"){
            homeScreen()

        }

        composable(route="chat"){
            chatScreen()

        }

        composable(route="settings"){
            settingsScreen()

        }
    }
}

/*
we need to access the current route to check if the specific item should be display as selected or not selected. if it is selected
it is highlight as green. To check that we need the current route of the navController and we have to compare it if that is
actually the same as the route of the current navigation item.
 */

/*
selected = item.route==navController.currentDestination?.route
means selected =true if item.route is equal to navController current route.
 */
@Composable
fun bottomNavigationBar(
    items:List<BottomNavItem>,
    navController: NavHostController,
    modifier: Modifier=Modifier,
    onItemClick:(BottomNavItem)->Unit
){
    var backStackEntry=navController.currentBackStackEntryAsState()

    BottomNavigation(modifier = modifier,
    backgroundColor = Color.DarkGray,
    elevation = 5.dp) {
        items.forEach{item->
            var selected=item.route==backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if(item.badgeCount>0){
                            BadgedBox(
                                badge = {
                                    Text(text = item.badgeCount.toString())
                                }
                            ) {
                                Icon(
                                    imageVector = item.image,
                                    contentDescription = null
                                )
                                
                            }
                        }else{
                            Icon(
                                imageVector = item.image,
                                contentDescription = null
                            )
                        }

                        if(selected){
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                        
                    }

                }
            )

        }

    }

}

@Composable
fun homeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home Screen")
    }
}

@Composable
fun chatScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Chat Screen")
    }

}

@Composable
fun settingsScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Settings Screen")
    }

}
