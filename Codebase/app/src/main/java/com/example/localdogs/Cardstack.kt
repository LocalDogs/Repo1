package com.example.localdogs


//package com.yuyakaido.android.cardstackview.sample

//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.example.localdogs.DogFilter.DogFilterActivity
import com.example.localdogs.data.Dog
import com.example.localdogs.data.awsinterface.Authentication
import com.example.localdogs.data.awsinterface.api.ApiResources
import com.example.localdogs.data.awsinterface.api.DogRequests
import com.example.localdogs.data.awsinterface.api.response.CardStackDogList
import com.example.localdogs.data.awsinterface.api.response.DogFilterResult
import com.example.localdogs.ui.CardStackAdapter
import com.example.localdogs.ui.ThreadSafeToast
import com.example.localdogs.ui.login.LoginActivity
import com.google.android.material.navigation.NavigationView
import com.yuyakaido.android.cardstackview.*


//class MainActivity : AppCompatActivity(), CardStackListener {
class Cardstack : AppCompatActivity(), CardStackListener, NavigationView.OnNavigationItemSelectedListener {

    private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }
    private val cardStackView by lazy { findViewById<CardStackView>(R.id.card_stack_view) }
    private val manager by lazy { CardStackLayoutManager(this, this) }
    private val adapter by lazy { CardStackAdapter(Cards().spots) }
    private val doglist by lazy { CardStackDogList.getInstance(this.applicationContext).dogs }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardstack)
        setupNavigation()
        setupCardStackView()
        setupButton()

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar);
        setSupportActionBar(toolbar);

        val navigationView = findViewById<NavigationView>(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    private fun initialize() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    //moveDogSuccessesToStack(doglist)
        fillAllDogs()
    }

    private fun fillAllDogs(){
        for(d in doglist){
            addNewSpot(Spot(d))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        /*
        when(item.itemId) {
            R.id.nav_usersettings -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
            FragmentUserSettings()).commit();
        }
        */
        var id = item.itemId;
        if (id == R.id.nav_matches) {
            /*
            * TODO: When Jackson finishes working on Matches page, fix it, because it crashes when loading the activity
            *  For now, the click will do nothing, to prevent crashing
             */
            val cinemaIntent = Intent(this, Matches::class.java)
            startActivity(cinemaIntent)
        }
        else if (id == R.id.nav_settings) {
            val cinemaIntent = Intent(this, UserSettings::class.java)
            startActivity(cinemaIntent)
        }
        else if (id == R.id.nav_filter) {
            val cinemaIntent = Intent(this, DogFilterActivity::class.java)
            startActivity(cinemaIntent)
        }
        else if (id == R.id.nav_map) {
            val cinemaIntent = Intent(this, MapsActivity::class.java)
            startActivity(cinemaIntent)
        }
        else if (id == R.id.nav_logout) {

            //*****NEEDS A WAY TO LOG OUT PROPERLY
            Authentication.getInstance(applicationContext).signOutUser({
                run {
                    val cinemaIntent = Intent(this, LoginActivity::class.java)
                    startActivity(cinemaIntent)
                    Log.i("Auth", "this lambda is not passed a parameter")
                    ThreadSafeToast.makeText(applicationContext, "Logout succeeded!", Toast.LENGTH_SHORT).show()
                    //If there is a valid login after pressing keyboard enter key. End activity
                    finish();
                }
            }, { error ->
                run {
                    val cinemaIntent = Intent(this, LoginActivity::class.java)
                    startActivity(cinemaIntent)
                    Log.e("Auth", error.recoverySuggestion)
                    ThreadSafeToast.makeText(applicationContext, "Logout succeeded!", Toast.LENGTH_SHORT).show()
                    finish();
                }
            })

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCardDragging(direction: Direction, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
        if (manager.topPosition == adapter.itemCount - 5) {
            paginate()
        }
    }

    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardAppeared: ($position) ${textView.text}")
    }

    override fun onCardDisappeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardDisappeared: ($position) ${textView.text}")
    }

    private fun setupNavigation() {
        // Toolbar
        /*val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // DrawerLayout
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        actionBarDrawerToggle.syncState()
        drawerLayout.addDrawerListener(actionBarDrawerToggle)

        // NavigationView
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.reload -> reload()
                R.id.add_spot_to_first -> addFirst(1)
                R.id.add_spot_to_last -> addLast(1)
                R.id.remove_spot_from_first -> removeFirst(1)
                R.id.remove_spot_from_last -> removeLast(1)
                R.id.replace_first_spot -> replace()
                R.id.swap_first_for_last -> swap()
            }
            drawerLayout.closeDrawers()
            true
        }*/
    }

    private fun setupCardStackView() {
        initialize()
    }

    private fun setupButton() {
        val skip = findViewById<View>(R.id.skip_button)
        skip.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        val rewind = findViewById<View>(R.id.rewind_button)
        rewind.setOnClickListener {
            val setting = RewindAnimationSetting.Builder()
                    .setDirection(Direction.Bottom)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(DecelerateInterpolator())
                    .build()
            manager.setRewindAnimationSetting(setting)
            cardStackView.rewind()
        }

        val like = findViewById<View>(R.id.like_button)
        like.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }
    }

    private fun loadDefaultCards(){
        val old = adapter.getSpots()
        val cards = Cards()
        //cards.defaultDogs()
        val new = cards.spots
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun paginate() {
        loadDefaultCards()
        addNewSpot(Spot("Charles", "Good Boy", "https://preview.redd.it/mo3qwb4xjtw51.jpg"))
    }
    private fun addNewSpot(spot: Spot){
        val old = adapter.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            add(manager.topPosition, spot)
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }
    private fun emptyOut(){
        val old = adapter.getSpots()
        val new = mutableListOf<Spot>().apply {}
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    /*
    fun settingsClick(view: View) {
        val intent = Intent(this, UserSettings::class.java)
        startActivity(intent)
    }
    fun filterClick(view: View) {
        val intent = Intent(this, DogFilterActivity::class.java)
        startActivity(intent)
    }*/

    private fun addFirst(size: Int) {
        val old = adapter.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            for (i in 0 until size) {
                add(manager.topPosition, Spot())
            }
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun addLast(size: Int) {
        val old = adapter.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            addAll(List(size) { Spot() })
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun removeFirst(size: Int) {
        if (adapter.getSpots().isEmpty()) {
            return
        }

        val old = adapter.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            for (i in 0 until size) {
                removeAt(manager.topPosition)
            }
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun removeLast(size: Int) {
        if (adapter.getSpots().isEmpty()) {
            return
        }

        val old = adapter.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            for (i in 0 until size) {
                removeAt(this.size - 1)
            }
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun replace() {
        val old = adapter.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            removeAt(manager.topPosition)
            add(manager.topPosition, Spot())
        }
        adapter.setSpots(new)
        adapter.notifyItemChanged(manager.topPosition)
    }

    private fun swap() {
        val old = adapter.getSpots()
        val new = mutableListOf<Spot>().apply {
            addAll(old)
            val first = removeAt(manager.topPosition)
            val last = removeAt(this.size - 1)
            add(manager.topPosition, last)
            add(first)
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun filterBreeds(breeds: ArrayList<String>, whitelist: Boolean){
        val old = adapter.getSpots()
        emptyOut()
            for (x in old) {
                for (y in breeds) {
                    if (whitelist == x.dog.breeds.contains(y)){
                        addNewSpot(x)
                        continue
                    }
                }
            }
    }

    private fun filterAge(ageMin: Int, ageMax: Int){
        val old = adapter.getSpots()
        emptyOut()
        for(x in old){
            if((x.dog.getAge() >= ageMin) && (x.dog.getAge() <= ageMin))
                addNewSpot(x)
        }
    }

    private fun filterWeight(weight: Int, greaterThan: Boolean){
        val old = adapter.getSpots()
        emptyOut()
        for(x in old){
            if(greaterThan == (x.dog.weight > weight))
                addNewSpot(x)
        }
    }

    private fun filterActivityLevel(min: Int, max: Int){
        val old = adapter.getSpots()
        emptyOut()
        for(x in old){
            if((x.dog.weight >= min) && (x.dog.weight <= min))
                addNewSpot(x)
        }
    }

}